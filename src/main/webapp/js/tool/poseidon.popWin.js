		

//悬浮窗显示的函数
	
var popWinClosed=true;
var popWinShowing=null;

	function showPopWin(popWinId){
		popped=true;
		var $popWin=$('#'+popWinId)
		$shadowBox=$popWin.siblings('.sub_shadow');
		var shadowBox_left=$('#content').get(0).getBoundingClientRect().left;
		var shadowBox_top=$('#content').get(0).getBoundingClientRect().top;
		var popWin_left=shadowBox_left+($('#content').width()-$popWin.width())*0.5;
		var popWin_top=shadowBox_top+($('#content').height()-$popWin.height())*0.5;
		$shadowBox.css({'filter':'alpha(opacity=60)','opacity':0.6});
		$popWin.css({'left':left,'top':right,'display':'block'});
	}
//悬浮窗关闭函数	
	function closePopWin(popWinId){
	
		var $popWin=$('#'+popWinId);
		$popWin.css({'display':'none'});
		$shadowBox=$popWin.siblings('.sub_shadow');
		$shadowBox.css({'filter':'alpha(opacity=100)','opacity':1});
		popped=false;
	}
	

//悬浮窗的整合调用函数
function doPopWin(popWinId){
	var $popWin=$('#'+popWinId);
	if(popWinClosed){
		appState.setState('popWin');
		$shadowBox=$popWin.siblings('.sub_shadow');
		var shadowBox_left=$('#content').get(0).getBoundingClientRect().left;
		var shadowBox_top=$('#content').get(0).getBoundingClientRect().top;
		var popWin_left=shadowBox_left+($('#content').width()-$popWin.width())*0.5;
		var popWin_top=shadowBox_top+($('#content').height()-$popWin.height())*0.5;
		$shadowBox.css({'opacity':0.6});
		$popWin.css({'left':left,'top':right,'display':'block'});
		
		popWinShowing=popWinId;
		popWinClosed=!popWinClosed;
	}else if(popWinShowing==popWinId){
		appState.setState('popWin');
		if(!appState.isStateOk()) alert('!State NOT OK!');
		$popWin.css({'display':'none'});
		$shadowBox=$popWin.siblings('.sub_shadow');
		$shadowBox.css({'opacity':1});
		popWinShowing=null;
		popWinClosed=!popWinClosed;
	}else{
		
	}
	
	
}