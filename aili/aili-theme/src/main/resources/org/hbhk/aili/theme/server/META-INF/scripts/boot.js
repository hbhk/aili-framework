__CreateJSPath = function (js) {
    var scripts = document.getElementsByTagName("script");
    var path = "";
    for (var i = 0, l = scripts.length; i < l; i++) {
        var src = scripts[i].src;
        if (src.indexOf(js) != -1) {
            var ss = src.split(js);
            path = ss[0];
            break;
        }
    }
    var href = location.href;
    href = href.split("#")[0];
    href = href.split("?")[0];
    var ss = href.split("/");
    ss.length = ss.length - 1;
    href = ss.join("/");
    if (path.indexOf("https:") == -1 && path.indexOf("http:") == -1 && path.indexOf("file:") == -1 && path.indexOf("\/") != 0) {
        path = href + "/" + path;
    }
    return path;
}

var jsPATH = __CreateJSPath("boot.js");
var cssPATH = jsPATH.replace("scripts","styles");
//debugger
mini_debugger = true;   
//jquery
document.write('<script src="' + jsPATH + 'jquery/jquery-1.9.1.js" type="text/javascript"></script>');
document.write('<script src="' + jsPATH + 'jquery/jquery.cookie.js" type="text/javascript"></script>');
document.write('<script src="' + jsPATH + 'jquery-exp.js" type="text/javascript"></sc' + 'ript>');
//bootstrap
document.write('<script src="' + jsPATH + 'bootstrap/bootstrap.min.js" type="text/javascript"></script>');
document.write('<script src="' + jsPATH + 'bootstrap/bootstrapValidator.min.js" type="text/javascript"></script>');
//fancybox
document.write('<script src="' + jsPATH + 'fancybox/jquery.fancybox.js" type="text/javascript"></script>');
document.write('<script src="' + jsPATH + 'fancybox/jquery.fancybox.pack.js" type="text/javascript"></script>');
//鼠标移动
document.write('<script src="' + jsPATH + 'jquery/jquery.mousewheel-3.0.6.pack.js" type="text/javascript"></script>');
//common
document.write('<script src="' + jsPATH + 'common.js" type="text/javascript"></script>');
//css
document.write('<link rel="stylesheet" href="' +cssPATH + 'bootstrap/bootstrap.min.css" >');
document.write('<link rel="stylesheet" href="' +cssPATH + 'bootstrap/bootstrap-theme.min.css" >');
//fancybox css
document.write('<link rel="stylesheet" href="' +jsPATH + 'fancybox/jquery.fancybox.css" >');
//jquery ui
document.write('<link rel="stylesheet" href="' +jsPATH + 'jqueryui/jquery-ui.min.css" >');
document.write('<script src="' + jsPATH + 'jqueryui/jquery-ui.min.js" type="text/javascript"></script>');
//jqgrid
document.write('<link rel="stylesheet" href="' +jsPATH + 'jqgrid/ui.jqgrid.css" >');
document.write('<script src="' + jsPATH + 'jqgrid/i18n/grid.locale-en.js" type="text/javascript"></script>');
document.write('<script src="' + jsPATH + 'jqgrid/jquery.jqGrid.min.js" type="text/javascript"></script>');

////////////////////////////////////////////////////////////////////////////////////////
function getCookie(sName) {
    var aCookie = document.cookie.split("; ");
    var lastMatch = null;
    for (var i = 0; i < aCookie.length; i++) {
        var aCrumb = aCookie[i].split("=");
        if (sName == aCrumb[0]) {
            lastMatch = aCrumb;
        }
    }
    if (lastMatch) {
        var v = lastMatch[1];
        if (v === undefined) return v;
        return unescape(v);
    }
    return null;
}