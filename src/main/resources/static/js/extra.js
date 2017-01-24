/**
 *
 * Extra JS v1.0.1
 *
 * @name Extra JS
 * @author Ciprian Mocanu @ mbe - web agency - https://mbe.ro
 *
 */
var extra = {

	redirect: function(url) {
		window.location.href = url;
	},
	refresh: function() {
		location.reload();
	},
	inArray: function(needle, haystack) {
		var length = haystack.length;
		for(var i = 0; i < length; i++) {
			if(haystack[i] == needle) return true;
		}
		return false;
	},
	scrollTo: function(element) {
		var _element = jQuery(element);
		var _top = _element.offset().top;
		jQuery('body,html').animate({
			scrollTop : (parseInt(_top, 10) - 273)
		}, 'slow');
	},

	createCookie: function (name,value,days) {
		var expires;
		if (days) {
			var date = new Date();
			date.setTime(date.getTime()+(days*24*60*60*1000));
			expires = "; expires="+date.toGMTString();
		} else {
			expires = "";
		}
		document.cookie = name+"="+value+expires+"; path=/";
	},
	createShortCookie: function (name,value,days) {
		var expires;
		if (days) {
			var date = new Date();
			date.setTime(date.getTime()+(3*60*60*1000));
			expires = "; expires="+date.toGMTString();
		} else {
			expires = "";
		}
		document.cookie = name+"="+value+expires+"; path=/";
	},
	readCookie: function (name) {
		var nameEQ = name + "=";
		var ca = document.cookie.split(';');
		for (var i=0; i < ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0)==' ') {
				c = c.substring(1,c.length);
			}
			if (c.indexOf(nameEQ) === 0) {
				return c.substring(nameEQ.length,c.length);
			}
		}
		return null;
	},
	eraseCookie: function (name) {
		extra.createCookie(name,"",-1);
	},
	getOS: function() {
		var ua = navigator.userAgent.toLowerCase();
		return {
			isWin2K: /windows nt 5.0/.test(ua),
			isXP: /windows nt 5.1/.test(ua),
			isVista: /windows nt 6.0/.test(ua),
			isWin7: /windows nt 6.1/.test(ua),
			isMac: /mac/.test(ua)
		};
	}
};
