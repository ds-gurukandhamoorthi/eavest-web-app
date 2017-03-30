/**
 * 
 */
package com.synovia.digital.service;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.synovia.digital.dto.PrdProductDto;
import com.synovia.digital.dto.utils.DtoUtils;
import com.synovia.digital.exceptions.EavDuplicateEntryException;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.exceptions.EavTechnicalException;
import com.synovia.digital.exceptions.utils.EavErrorCode;
import com.synovia.digital.filedataware.EavHomeDirectory;
import com.synovia.digital.filedataware.EavResource;
import com.synovia.digital.model.PrdCouponDate;
import com.synovia.digital.model.PrdEarlierRepaymentDate;
import com.synovia.digital.model.PrdObservationDate;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.model.PrdRule;
import com.synovia.digital.model.PrdUser;
import com.synovia.digital.repository.PrdProductRepository;
import com.synovia.digital.repository.PrdSousJacentRepository;
import com.synovia.digital.repository.PrdStatusRepository;
import com.synovia.digital.service.utils.PrdUserProductComparator;
import com.synovia.digital.service.utils.RefundProductComparator;
import com.synovia.digital.utils.EavUtils;
import com.synovia.digital.utils.FileExtractor;
import com.synovia.digital.utils.PrdStatusEnum;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 17 févr. 2017
 */
@Service
public class PrdProductServiceImpl implements PrdProductService {

	protected final PrdProductRepository repo;

	protected final PrdSousJacentRepository sousJacentRepo;

	protected final PrdStatusRepository statusRepo;

	@Autowired
	protected PrdObservationDateService obsDateService;

	//	@Autowired
	//	protected PrdCouponDateService couponDateService;

	@Autowired
	protected PrdEarlierRepaymentDateService earlyRepayDateService;

	@Autowired
	protected PrdUserService userService;

	@Autowired
	protected EavResource eavResource;

	protected final EavHomeDirectory homeDir;

	private static final Logger LOGGER = LoggerFactory.getLogger(PrdProductServiceImpl.class);

	/**
	 * TODO Constructs ... based on ...
	 *
	 */
	@Autowired
	public PrdProductServiceImpl(PrdProductRepository repo, PrdSousJacentRepository ssjctRepo,
			PrdStatusRepository statusRepo, EavHomeDirectory homeDir) {
		this.repo = repo;
		this.sousJacentRepo = ssjctRepo;
		this.statusRepo = statusRepo;
		this.homeDir = homeDir;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdProductService#findById(java.lang.Long)
	 */
	@Override
	public PrdProduct findById(Long id) throws EavEntryNotFoundException {
		PrdProduct toFind = repo.findOne(id);
		if (toFind == null)
			throw new EavEntryNotFoundException(PrdProduct.class.getTypeName());

		return toFind;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdProductService#add(com.synovia.digital.dto.
	 * PrdProductDto)
	 */
	@Override
	public PrdProduct add(PrdProductDto dto) throws EavTechnicalException {
		LOGGER.debug("Adding a new PrdProduct entry with information: {}", dto);
		if (dto == null)
			return null;

		// Search for an existing entry with the same label.
		PrdProduct duplicate = repo.findByIsin(dto.getIsin());
		if (duplicate != null)
			throw new EavDuplicateEntryException(PrdProduct.class.getTypeName());

		// Create the PrdProduct object.
		PrdProduct toAdd = convertToEntity(dto);

		// Apply basic rules from PrdProduct fields
		postUpdate(toAdd);

		// Save the object to add.
		return repo.save(toAdd);
	}

	private void postUpdate(PrdProduct product) throws EavTechnicalException {
		try {
			// Update effective end date
			postUpdateEndDate(product);
			// update product status
			postUpdateStatus(product);
		} catch (NullPointerException e) {
			throw new EavTechnicalException(EavErrorCode.NULL_POINTER, e);
		}
	}

	private void postUpdateEndDate(PrdProduct product) {
		Date endDate = product.getEndDate();
		if (endDate == null) {
			Date now = new Date();
			if (now.after(product.getDueDate())) {
				product.setEndDate(product.getDueDate());
			}
		}
	}

	private void postUpdateStatus(PrdProduct product) {
		Date now = new Date();
		Date finalDate = product.getEndDate() != null ? product.getEndDate() : product.getDueDate();

		if (now.before(product.getLaunchDate())) {
			product.setPrdStatus(statusRepo.findByCode(PrdStatusEnum.IDLE.name()));

		} else if (now.after(product.getLaunchDate()) && now.before(finalDate)) {
			product.setPrdStatus(statusRepo.findByCode(PrdStatusEnum.ON_GOING.name()));

		} else if (now.after(finalDate)) {
			if (finalDate.before(product.getDueDate())) {
				product.setPrdStatus(statusRepo.findByCode(PrdStatusEnum.PREPAYED.name()));
			} else {
				product.setPrdStatus(statusRepo.findByCode(PrdStatusEnum.REFUNDED.name()));
			}
		}
	}

	private void updateFromDto(PrdProduct entity, PrdProductDto dto) {
		// TODO A status automatically sets the end date and vice versa
		if (dto.getLabel() != null) {
			entity.setLabel(dto.getLabel());
		}

		if (dto.getLaunchDate() != null) {
			// Be sure that the new launch date is before the due date
			Date dueDate = null;
			try {
				dueDate = dto.getDueDateObject();
			} catch (ParseException e) {
				dueDate = entity.getDueDate();
			}
		}
	}

	private PrdProduct convertToEntity(PrdProductDto dto) {
		PrdProduct entity = new PrdProduct();

		entity.setId(dto.getId() != null ? dto.getId() : null);
		entity.setIsin(dto.getIsin() != null ? dto.getIsin() : null);
		entity.setLabel(dto.getLabel() != null ? dto.getLabel() : null);
		try {
			entity.setLaunchDate(dto.getLaunchDate() != null ? dto.getLaunchDateObject() : null);
		} catch (ParseException e) {
			LOGGER.debug("Invalid date format for argument [launchDate]");
		}
		try {
			entity.setDueDate(dto.getDueDate() != null ? dto.getDueDateObject() : null);
		} catch (ParseException e) {
			LOGGER.debug("Invalid date format for argument [dueDate]");
		}
		entity.setPrdSousJacent(
				dto.getIdPrdSousJacent() != null ? sousJacentRepo.findOne(dto.getIdPrdSousJacent()) : null);
		entity.setPrdRule(
				new PrdRule(dto.getProtectionBarrier(), dto.getCouponBarrier(), dto.getReimbursementBarrier()));
		if (dto.getObservationDates() != null) {
			Set<PrdObservationDate> obsDates = new HashSet<>();
			try {
				for (Date d : DtoUtils.convertAsList(dto.getObservationDates())) {
					obsDates.add(new PrdObservationDate(d));
				}
			} catch (ParseException e) {
				obsDates = null;
				LOGGER.debug("Invalid date format for argument [observationDates]");
			}
			entity.setObservationDates(obsDates);
		}
		if (dto.getEarlyRepaymentDates() != null) {
			Set<PrdEarlierRepaymentDate> dates = new HashSet<>();
			try {
				for (Date d : DtoUtils.convertAsList(dto.getEarlyRepaymentDates())) {
					dates.add(new PrdEarlierRepaymentDate(d));
				}
			} catch (ParseException e) {
				dates = null;
				LOGGER.debug("Invalid date format for argument [earlyRepaymentDates]");
			}
			entity.setEarlyRepaymentDates(dates);
		}
		if (dto.getCouponPaymentDates() != null) {
			Set<PrdCouponDate> dates = new HashSet<>();
			try {
				for (Date d : DtoUtils.convertAsList(dto.getCouponPaymentDates())) {
					dates.add(new PrdCouponDate(d));
				}
			} catch (ParseException e) {
				dates = null;
				LOGGER.debug("Invalid date format for argument [couponPaymentDates]");
			}
			entity.setCouponPaymentDates(dates);
		}
		try {
			entity.setSubscriptionStartDate(
					dto.getSubscriptionStartDate() != null ? dto.getSubscriptionStartDateObject() : null);
		} catch (ParseException e) {
			LOGGER.debug("Invalid date format for argument [subscriptionStartDate]");
		}
		try {
			entity.setSubscriptionEndDate(
					dto.getSubscriptionEndDate() != null ? dto.getSubscriptionEndDateObject() : null);
		} catch (ParseException e) {
			LOGGER.debug("Invalid date format for argument [subscriptionEndDate]");
		}
		entity.setCouponValue(dto.getCouponValue() != null ? dto.getCouponValue() : null);
		entity.setNominalValue(dto.getNominalValue() != null ? dto.getNominalValue() : null);
		entity.setCapitalGuaranteed(dto.getCapitalGuaranteed() != null ? dto.getCapitalGuaranteed() : null);
		entity.setStartPrice(dto.getStartPrice() != null ? dto.getStartPrice() : null);
		entity.setDeliver(dto.getDeliver() != null ? dto.getDeliver() : null);
		entity.setGuarantor(dto.getGuarantor() != null ? dto.getGuarantor() : null);
		try {
			entity.setPrdStatus(dto.getStatusCode() != null
					? statusRepo.findByCode(PrdStatusEnum.valueOf(dto.getStatusCode()).toString())
					: statusRepo.findByCode(PrdStatusEnum.IDLE.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			entity.setPrdStatus(statusRepo.findByCode(PrdStatusEnum.IDLE.toString()));
		}
		try {
			entity.setEndDate(dto.getEndDate() != null ? dto.getEndDateObject() : null);
		} catch (ParseException e) {
			LOGGER.debug("Invalid date format for argument [endDate]");
		}
		if (dto.getIsEavest() != null) {
			entity.setIsEavest(dto.getIsEavest());
		}
		if (dto.getIsBestSeller() != null) {
			entity.setIsBestSeller(dto.getIsBestSeller());
		}
		entity.setPath(dto.getPath() != null ? dto.getPath() : null);
		entity.setStrike(dto.getStrike() != null ? dto.getStrike() : null);
		entity.setObservationFrequency(dto.getObservationFrequency() != null ? dto.getObservationFrequency() : null);

		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdProductService#findAll()
	 */
	@Override
	public List<PrdProduct> findAll() {
		return repo.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdProductService#listRefundProducts(java.util.Date)
	 */
	@Override
	public List<PrdProduct> listRefundProducts(Date from) {
		List<PrdProduct> list = (from == null) ? new ArrayList<>() : repo.findByEndDateAfter(from);
		Collections.sort(list, new RefundProductComparator());

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdProductService#listUpcomingProducts(java.util.Date)
	 */
	@Override
	public List<PrdProduct> listUpcomingProducts(Date from, Date until) {
		// Retrieve the list of a filter of observation dates
		List<PrdObservationDate> obsDates = obsDateService.filterByDate(from, until);
		List<PrdProduct> results = new ArrayList<>();
		for (PrdObservationDate d : obsDates) {
			PrdProduct p = d.getPrdProduct();
			if (p.getEndDate() == null || d.getDate().before(p.getEndDate())) {
				results.add(p);
			}
		}
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdProductService#generatePackedName(com.synovia.
	 * digital.model.PrdProduct)
	 */
	@Override
	public String generatePackedName(PrdProduct product) {
		if (product == null)
			return null;

		return new StringBuilder(product.getDeliver()).append(", ").append(product.getIsin()).append(", ")
				.append(product.getLabel()).append(", ").append(product.getPrdSousJacent().getLabel()).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdProductService#generatePackedName(java.lang.Long)
	 */
	@Override
	public String generatePackedName(Long id) {
		return generatePackedName(repo.findOne(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdProductService#getPackedNameList(java.util.
	 * Collection)
	 */
	@Override
	public List<String> getPackedNameList(Collection<PrdProduct> products) {
		List<String> l = new ArrayList<>();
		for (PrdProduct p : products) {
			String n;
			l.add(n = generatePackedName(p));
			System.out.println(n);
		}
		return l;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdProductService#setBestSeller(com.synovia.digital.dto
	 * .PrdProductDto)
	 */
	@Override
	public PrdProduct setBestSeller(PrdProductDto dto) throws EavEntryNotFoundException {
		if (dto == null || dto.getIsin() == null) {
			LOGGER.warn("Unable to set the best-seller product, entry is null or the ISIN code is not filled");
			return null;
		}
		// Find the corresponding product from the DTO
		PrdProduct p = repo.findByIsin(dto.getIsin());
		if (p == null)
			throw new EavEntryNotFoundException(PrdProduct.class.getTypeName());

		// TODO Update the product
		//updateFromDto(p, dto);
		p.setIsBestSeller(true);

		return repo.save(p);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdProductService#findBestSeller()
	 */
	@Override
	public PrdProduct findBestSeller() throws EavTechnicalException {
		List<PrdProduct> bestSellers = repo.findByIsBestSeller(true);
		// Prevent technical errors
		if (bestSellers == null)
			throw new EavTechnicalException(EavErrorCode.NULL_POINTER);

		// Verify that there is only one best-seller
		if (bestSellers.size() > 1)
			throw new EavTechnicalException(EavErrorCode.MULTIPLE_BESTSELLER);

		PrdProduct result = null;
		if (!bestSellers.isEmpty()) {
			result = bestSellers.get(0);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdProductService#getImage(com.synovia.digital.model.
	 * PrdProduct)
	 */
	@Override
	public File getImage(PrdProduct product) {
		File bestImage = null;
		if (product == null)
			return bestImage;

		File[] images = homeDir.getImageDir(product.getId()).listFiles();
		for (File image : images) {
			if (bestImage == null || (image.length() > bestImage.length())) {
				if (image.length() < EavHomeDirectory.IMAGE_MAX_SIZE_BYTE) {
					bestImage = image;
				}
			}
		}
		return bestImage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdProductService#storeData(com.synovia.digital.model.
	 * PrdProduct)
	 */
	@Override
	public void storeDocuments(PrdProduct product) {
		// Open the product path where to find the product documents
		String pathname = product == null ? null : product.getPath();
		if (pathname == null)
			return;

		File dir = new File(pathname);
		// Control that the directory is valid
		if (!validateProductDirectory(dir))
			return;

		// Copy the available files into the FDWH
		try {
			FileExtractor.Param images = new FileExtractor.Param(EavUtils.JPEG_EXTENSION,
					homeDir.getImageDir(product.getId()));
			FileExtractor.copy(dir, images);
			// TODO Copy the other documents

		} catch (EavTechnicalException e) {
			LOGGER.error("Unable to extract files from input file {}", dir);
		}

	}

	private boolean validateProductDirectory(File dir) {
		return dir.exists() && dir.isDirectory() && dir.listFiles().length > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdProductService#getBestSellerImage()
	 */
	@Override
	public File getBestSellerImage() {
		File img = null;
		PrdProduct bestSeller = null;
		try {
			bestSeller = findBestSeller();
		} catch (EavTechnicalException e) {
			LOGGER.warn("Multiple best-seller are set. There should be only one.");
		}

		if (bestSeller != null) {
			img = getImage(bestSeller);
		}

		return img;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdProductService#storeImage(com.synovia.digital.model.
	 * PrdProduct, org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public void storeImage(PrdProduct product, MultipartFile fileToStore) throws EavTechnicalException {
		if (product == null)
			throw new EavEntryNotFoundException(PrdProduct.class.getTypeName());

		String imageName = getDefaultImageName(EavUtils.JPEG_EXTENSION);
		// Store the product image in the FDWH
		FileExtractor.Param imageParam = new FileExtractor.Param(EavUtils.JPEG_EXTENSION,
				homeDir.getImageDir(product.getId()), imageName);

		FileExtractor.copy(fileToStore, imageParam);
	}

	private String getDefaultImageName(String extension) {
		return EavUtils.constructDefaultDocumentName(extension, EavUtils.DEFAULT_IMAGE_NAME);
	}

	private String getDefaultTermSheetDocName(String extension) {
		return EavUtils.constructDefaultDocumentName(extension, EavUtils.DEFAULT_TS_NAME);
	}

	private String getDefaultMarketingDocName(String extension) {
		return EavUtils.constructDefaultDocumentName(extension, EavUtils.DEFAULT_MARKET_DOC_NAME);
	}

	private String getDefaultFeaseDocName(String extension) {
		return EavUtils.constructDefaultDocumentName(extension, EavUtils.DEFAULT_FEASE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdProductService#storeImage(java.lang.Long,
	 * org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public void storeImage(Long id, MultipartFile fileToStore) throws EavTechnicalException {
		// Find the product
		PrdProduct product = repo.findOne(id);
		// Store the input file
		storeImage(product, fileToStore);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdProductService#storeTermSheet(java.lang.Long,
	 * org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public void storeTermSheet(PrdProduct product, MultipartFile fileToStore) throws EavTechnicalException {
		if (product == null)
			throw new EavEntryNotFoundException(PrdProduct.class.getTypeName());

		String tsName = getDefaultTermSheetDocName(EavUtils.PDF_EXTENSION);
		// Store the product image in the FDWH
		FileExtractor.Param param = new FileExtractor.Param(EavUtils.PDF_EXTENSION,
				homeDir.getTermSheetDir(product.getId()), tsName);

		FileExtractor.copy(fileToStore, param);

		// Update product information
		product.setHasTermSheet(getTermSheet(product) != null);
		repo.save(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdProductService#storeFease(java.lang.Long,
	 * org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public void storeFease(PrdProduct product, MultipartFile fileToStore) throws EavTechnicalException {
		if (product == null)
			throw new EavEntryNotFoundException(PrdProduct.class.getTypeName());

		String feaseName = getDefaultFeaseDocName(EavUtils.PDF_EXTENSION);
		// Store the product image in the FDWH
		FileExtractor.Param param = new FileExtractor.Param(EavUtils.PDF_EXTENSION,
				homeDir.getFeaseDir(product.getId()), feaseName);

		FileExtractor.copy(fileToStore, param);

		// Update product information
		product.setHasFease(getFease(product) != null);
		repo.save(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdProductService#storeMarketingDoc(java.lang.Long,
	 * org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public void storeMarketingDoc(PrdProduct product, MultipartFile fileToStore) throws EavTechnicalException {
		if (product == null)
			throw new EavEntryNotFoundException(PrdProduct.class.getTypeName());

		String docName = getDefaultMarketingDocName(EavUtils.PDF_EXTENSION);
		// Store the product image in the FDWH
		FileExtractor.Param param = new FileExtractor.Param(EavUtils.PDF_EXTENSION,
				homeDir.getMarketingDir(product.getId()), docName);

		FileExtractor.copy(fileToStore, param);

		// Update product information
		product.setHasMarketingDoc(getMarketingDoc(product) != null);
		repo.save(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdProductService#getUserProducts(com.synovia.digital.
	 * model.PrdUser)
	 */
	@Override
	public List<PrdProduct> getUserProducts(PrdUser user) {
		if (user == null)
			return null;

		Set<PrdUser> users = new HashSet<>();
		users.add(user);

		List<PrdProduct> result = repo.findByPrdUsers(users);
		Collections.sort(result, new PrdUserProductComparator());

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdProductService#getFease(com.synovia.digital.model.
	 * PrdProduct)
	 */
	@Override
	public File getFease(PrdProduct product) {
		File fease = null;
		if (product == null)
			return fease;

		File[] docs = homeDir.getFeaseDir(product.getId()).listFiles();
		if (docs.length > 1) {
			LOGGER.error("Several documents exist but only one reference is required for product {}",
					product.getLabel());

		} else {
			if (docs.length == 0) {
				LOGGER.warn("No reference document found for product {}", product.getLabel());
			} else {
				fease = docs[0];

			}
		}
		return fease;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdProductService#getTermSheet(com.synovia.digital.
	 * model.PrdProduct)
	 */
	@Override
	public File getTermSheet(PrdProduct product) {
		File result = null;
		if (product == null)
			return result;

		File[] docs = homeDir.getTermSheetDir(product.getId()).listFiles();
		if (docs.length > 1) {
			LOGGER.error("Several documents exist but only one reference is required for product {}",
					product.getLabel());

		} else {
			if (docs.length == 0) {
				LOGGER.warn("No reference document found for product {}", product.getLabel());
			} else {
				result = docs[0];

			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdProductService#getMarketingDoc(com.synovia.digital.
	 * model.PrdProduct)
	 */
	@Override
	public File getMarketingDoc(PrdProduct product) {
		File result = null;
		if (product == null)
			return result;

		File[] docs = homeDir.getMarketingDir(product.getId()).listFiles();
		if (docs.length > 1) {
			LOGGER.error("Several documents exist but only one reference is required for product {}",
					product.getLabel());

		} else {
			if (docs.length == 0) {
				LOGGER.warn("No reference document found for product {}", product.getLabel());
			} else {
				result = docs[0];

			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdProductService#listUserRefundProducts(java.util.
	 * Date, com.synovia.digital.model.PrdUser)
	 */
	@Override
	public List<PrdProduct> listUserRefundProducts(Date from, PrdUser user) {
		Set<PrdUser> u = new HashSet<>();
		u.add(user);
		List<PrdProduct> list = (from == null) ? new ArrayList<>() : repo.findByEndDateAfterAndPrdUsers(from, u);
		Collections.sort(list, new RefundProductComparator());

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdProductService#listUserUpcomingProducts(java.util.
	 * Date, java.util.Date, com.synovia.digital.model.PrdUser)
	 */
	@Override
	public List<PrdProduct> listUserUpcomingProducts(Date from, Date until, PrdUser user) {
		// Retrieve the list of a filter of observation dates
		List<PrdObservationDate> obsDates = obsDateService.filterByDate(from, until);
		List<PrdProduct> results = new ArrayList<>();
		for (PrdObservationDate d : obsDates) {
			PrdProduct p = d.getPrdProduct();
			if (p.getPrdUsers().contains(user) && (p.getEndDate() == null || d.getDate().before(p.getEndDate()))) {
				results.add(p);
			}
		}
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdProductService#findAll(int, int)
	 */
	@Override
	public Page<PrdProduct> findAll(int pageIdx, int nbMaxProducts) {
		PageRequest pr = new PageRequest(pageIdx, nbMaxProducts, Sort.Direction.DESC, "launchDate");

		return repo.findAll(pr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdProductService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) throws EavEntryNotFoundException {
		// Find the corresponding entity
		PrdProduct product = this.findById(id);

		// TODO

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdProductService#delete(com.synovia.digital.model.
	 * PrdProduct)
	 */
	@Override
	public void delete(PrdProduct product) {
		//		// Delete all product date entities
		//		for (PrdCouponDate e : product.getCouponPaymentDates()) {
		//			couponDateService.delete(e);
		//		}
		//		for (PrdObservationDate d : product.getObservationDates()) {
		//			obsDateService.delete(d);
		//		}
		//		for (PrdEarlierRepaymentDate d : product.getEarlyRepaymentDates()) {
		//			earlyRepayDateService.delete(d);
		//		}
		//
		//		// Remove the product from user's wallet
		//		for (PrdUser u : product.getPrdUsers()) {
		//			userService.removeProduct(u, product);
		//		}

		repo.delete(product);
	}

}
