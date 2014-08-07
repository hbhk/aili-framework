<title></title>
<style>
.ddsmoothmenu {
	MARGIN: 0px auto; FONT: 12px Verdana; WIDTH: 730px
}
.ddsmoothmenu UL {
	PADDING-RIGHT: 0px; PADDING-LEFT: 0px;BACKGROUND: #2b9801;  Z-INDEX: 100; FLOAT: left; PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-TOP: 0px; LIST-STYLE-TYPE: none
}
.ddsmoothmenu UL LI {
	DISPLAY: block; FLOAT: left; WIDTH: 81px; LINE-HEIGHT: 31px; POSITION: relative; TEXT-ALIGN: center
}
 HTML .ddsmoothmenu UL LI {
	FLOAT: left; WIDTH: 81px; LINE-HEIGHT: 31px; POSITION: relative; TEXT-ALIGN: center
}
.ddsmoothmenu UL LI A {
	DISPLAY: block; FONT-WEIGHT: bold; WIDTH: 81px; TEXT-DECORATION: none
}
.ddsmoothmenu UL LI A:link {
	COLOR: #fff
}
.ddsmoothmenu UL LI A:visited {
	COLOR: #fff
}
.ddsmoothmenu UL LI A:hover {
	COLOR: #ffff00
}
.ddsmoothmenu UL LI UL {
	LEFT: 0px; VISIBILITY: hidden; POSITION: absolute
}
.ddsmoothmenu UL LI UL LI {
	BACKGROUND: #2b9801; FLOAT: left; WIDTH: 81px; LINE-HEIGHT: 25px; BORDER-BOTTOM: #96d47d 1px solid
}
.ddsmoothmenu UL LI UL LI A {
	DISPLAY: block; WIDTH: 81px; TEXT-DECORATION: none
}
.ddsmoothmenu UL LI UL LI A:hover {
	BACKGROUND: #51b228
}
.ddsmoothmenu UL LI UL LI UL {
	TOP: 0px
}
.downarrowclass {
	DISPLAY: none; POSITION: absolute
}
.rightarrowclass {
	DISPLAY: none; POSITION: absolute
}
.ddshadow {
	BACKGROUND: silver; LEFT: 0px; WIDTH: 0px; POSITION: absolute; TOP: 0px; HEIGHT: 0px
}
.toplevelshadow {
	opacity: 0.8
}
</style>
<SCRIPT src="http://www.codefans.net/ajaxjs/jquery1.3.2.js" type=text/javascript></SCRIPT>
<SCRIPT type=text/javascript>
var ddsmoothmenu={

//Specify full URL to down and right arrow images (23 is padding-right added to top level LIs with drop downs):
arrowimages: {down:['downarrowclass', '', 0], right:['rightarrowclass', '']},
transition: {overtime:300, outtime:300}, //duration of slide in/ out animation, in milliseconds
shadow: {enabled:false, offsetx:5, offsety:5},
detectwebkit: navigator.userAgent.toLowerCase().indexOf("applewebkit")!=-1, //detect WebKit browsers (Safari, Chrome etc)
getajaxmenu:function($, setting){ //function to fetch external page containing the panel DIVs
	var $menucontainer=$('#'+setting.contentsource[0]) //reference empty div on page that will hold menu
	$menucontainer.html("Loading Menu...")
	$.ajax({
		url: setting.contentsource[1], //path to external menu file
		async: true,
		error:function(ajaxrequest){
			$menucontainer.html('Error fetching content. Server Response: '+ajaxrequest.responseText)
		},
		success:function(content){
			$menucontainer.html(content)
			ddsmoothmenu.buildmenu($, setting)
		}
	})
},

buildshadow:function($, $subul){
	
},

buildmenu:function($, setting){
	var smoothmenu=ddsmoothmenu
	var $mainmenu=$("#"+setting.mainmenuid+">ul") //reference main menu UL
	var $headers=$mainmenu.find("ul").parent()
	$headers.each(function(i){
		var $curobj=$(this).css({zIndex: 100-i}) //reference current LI header
		var $subul=$(this).find('ul:eq(0)').css({display:'block'})
		this._dimensions={w:this.offsetWidth, h:this.offsetHeight, subulw:$subul.outerWidth(), subulh:$subul.outerHeight()}
		this.istopheader=$curobj.parents("ul").length==1? true : false //is top level header?
		$subul.css({top:this.istopheader? this._dimensions.h+"px" : 0})
		$curobj.children("a:eq(0)").css(this.istopheader? {paddingRight: smoothmenu.arrowimages.down[2]} : {}).append( //add arrow images
			'<img src="'+ (this.istopheader? smoothmenu.arrowimages.down[1] : smoothmenu.arrowimages.right[1])
			+'" class="' + (this.istopheader? smoothmenu.arrowimages.down[0] : smoothmenu.arrowimages.right[0])
			+ '" style="border:0;" />'
		)
		if (smoothmenu.shadow.enabled){
			this._shadowoffset={x:(this.istopheader?$subul.offset().left+smoothmenu.shadow.offsetx : this._dimensions.w), y:(this.istopheader? $subul.offset().top+smoothmenu.shadow.offsety : $curobj.position().top)} //store this shadow's offsets
			if (this.istopheader)
				$parentshadow=$(document.body)
			else{
				var $parentLi=$curobj.parents("li:eq(0)")
				$parentshadow=$parentLi.get(0).$shadow
			}
			this.$shadow=$('<div class="ddshadow'+(this.istopheader? ' toplevelshadow' : '')+'"></div>').prependTo($parentshadow).css({left:this._shadowoffset.x+'px', top:this._shadowoffset.y+'px'})  //insert shadow DIV and set it to parent node for the next shadow div
		}
		$curobj.hover(
			function(e){
				var $targetul=$(this).children("ul:eq(0)")
				this._offsets={left:$(this).offset().left, top:$(this).offset().top}
				var menuleft=this.istopheader? 0 : this._dimensions.w
				menuleft=(this._offsets.left+menuleft+this._dimensions.subulw>$(window).width())? (this.istopheader? -this._dimensions.subulw+this._dimensions.w : -this._dimensions.w) : menuleft //calculate this sub menu's offsets from its parent
				if ($targetul.queue().length<=1){ //if 1 or less queued animations
					$targetul.css({left:menuleft+"px", width:this._dimensions.subulw+'px'}).animate({height:'show',opacity:'show'}, ddsmoothmenu.transition.overtime)
					if (smoothmenu.shadow.enabled){
						var shadowleft=this.istopheader? $targetul.offset().left+ddsmoothmenu.shadow.offsetx : menuleft
						var shadowtop=this.istopheader?$targetul.offset().top+smoothmenu.shadow.offsety : this._shadowoffset.y
						if (!this.istopheader && ddsmoothmenu.detectwebkit){ //in WebKit browsers, restore shadow's opacity to full
							this.$shadow.css({opacity:1})
						}
						this.$shadow.css({overflow:'', width:this._dimensions.subulw+'px', left:shadowleft+'px', top:shadowtop+'px'}).animate({height:this._dimensions.subulh+'px'}, ddsmoothmenu.transition.overtime)
					}
				}
			},
			function(e){
				var $targetul=$(this).children("ul:eq(0)")
				$targetul.animate({height:'hide', opacity:'hide'}, ddsmoothmenu.transition.outtime)
				if (smoothmenu.shadow.enabled){
					if (ddsmoothmenu.detectwebkit){ //in WebKit browsers, set first child shadow's opacity to 0, as "overflow:hidden" doesn't work in them
						this.$shadow.children('div:eq(0)').css({opacity:0})
					}
					this.$shadow.css({overflow:'hidden'}).animate({height:0}, ddsmoothmenu.transition.outtime)
				}
			}
		) //end hover
	}) //end $headers.each()
	$mainmenu.find("ul").css({display:'none', visibility:'visible'})
},

init:function(setting){
	if (typeof setting.customtheme=="object" && setting.customtheme.length==2){
		var mainmenuid='#'+setting.mainmenuid
		document.write('<style type="text/css">\n'
			+mainmenuid+', '+mainmenuid+' ul li a {background:'+setting.customtheme[0]+';}\n'
			+mainmenuid+' ul li a:hover {background:'+setting.customtheme[1]+';}\n'
		+'</style>')
	}
	jQuery(document).ready(function($){ //override default menu colors (default/hover) with custom set?
		if (typeof setting.contentsource=="object"){ //if external ajax menu
			ddsmoothmenu.getajaxmenu($, setting)
		}
		else{ //else if markup menu
			ddsmoothmenu.buildmenu($, setting)
		}
	})
}

} //end ddsmoothmenu variable

//Initialize Menu instance(s):

ddsmoothmenu.init({
	mainmenuid: "smoothmenu1", //menu DIV id
	customtheme: [], //override default menu CSS background values? Uncomment: ["normal_background", "hover_background"]
	contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
})
</SCRIPT>

<DIV class=ddsmoothmenu id=smoothmenu1>
<UL>
  <LI><A href="http://www.codefans.net">首页</A> 
  </LI>
  <LI><A href="#">一年级</A> 
  <UL>
    <LI><A href="#">一年级1班</A> </LI>
    <LI><A href="#">一年级2班</A> </LI>
    <LI><A href="#">一年级3班</A> </LI>
    <LI><A href="#">一年级4班</A> </LI>
    <LI><A href="#">一年级5班</A> 
  </LI></UL></LI>
  <LI><A href="#">二年级</A> 
  <UL>
    <LI><A href="#">二年级1班</A> </LI>
    <LI><A href="#">二年级2班</A> </LI>
    <LI><A href="#">二年级3班</A> </LI>
    <LI><A href="#">二年级4班</A> </LI>
    <LI><A href="#">二年级5班</A> 
  </LI></UL></LI>
  <LI><A href="#">三年级</A> 
  <UL>
    <LI><A href="#">三年级1班</A> </LI>
    <LI><A href="#">三年级2班</A> </LI>
    <LI><A href="#">三年级3班</A> </LI>
    <LI><A href="#">三年级4班</A> </LI>
    <LI><A href="#">三年级5班</A> 
  </LI></UL></LI>
<LI><A href="#">四年级</A> 
  <UL>
    <LI><A href="#">四年级1班</A> </LI>
    <LI><A href="#">四年级2班</A> </LI>
    <LI><A href="#">四年级3班</A> </LI>
    <LI><A href="#">四年级4班</A> </LI>
    <LI><A href="#">四年级5班</A> 
  </LI></UL></LI>
  <LI><A href="#">五年级</A> 
  <UL>
    <LI><A href="#">五年级1班</A> </LI>
    <LI><A href="#">五年级2班</A> </LI>
    <LI><A href="#">五年级3班</A> </LI>
    <LI><A href="#">五年级4班</A> </LI>
    <LI><A href="#">五年级5班</A> 
  </LI></UL></LI>
    <LI><A href="#">六年级</A> 
  <UL>
    <LI><A href="#">六年级1班</A> </LI>
    <LI><A href="#">六年级2班</A> </LI>
    <LI><A href="#">六年级3班</A> </LI>
    <LI><A href="#">六年级4班</A> </LI>
    <LI><A href="#">六年级5班</A> 
  </LI></UL></LI>
  <LI><A href="#">七年级</A> 
  <UL>
    <LI><A href="#">七年级1班</A> </LI>
    <LI><A href="#">七年级2班</A> </LI>
    <LI><A href="#">七年级3班</A> </LI>
    <LI><A href="#">七年级4班</A> </LI>
    <LI><A href="#">七年级5班</A> 
  </LI></UL></LI>
</UL>
</DIV>