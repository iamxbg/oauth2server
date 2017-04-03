

/**
 *   手动的阴影框,需找当前最近的.sub_shadow使其阴影化
 *   控制参数: --- subShadowClosed
 */


	var subShadowClosed=true;
	function doShadow(shadowBoxId){
		$shadowBox=$('#'+shadowBoxId);
		if(subShadowClosed){
			$shadowBox.css({'filter':'alpha(opacity=60)','opacity':0.6});
		}else{
			$shadowBox.css({'filter':'alpha(opacity=100)','opacity':1});
		}
		subShadowClosed=!subShadowClosed;
	}
	
	var searchWaitingClosed=true;
	function doSearchWaiting(shadowBoxId){
		$shadowBox=$('#'+shadowBoxId);
		if(searchWaitingClosed){
			$shadowBox.css({'filter':'alpha(opacity=60)','opacity':0.6});
		}else{
			$shadowBox.css({'filter':'alpha(opacity=100)','opacity':1});
		}
		searchWaitingClosed=!searchWaitingClosed;
	}

/**
 *   等待的阴影框
 */
$(function(){

	/*
	var waiting=false;
	// waitingTip需要动态在主窗口中配置

	var $waitingTip=$('#waitingTip');
	function doWaiting(waitingBoxId){
		
		var waitingBox=$('#'+waitingBoxId);
		if(!waiting){
			waitingBox.css({'filter':'alpha(opacity=60)','opacity':0.6});
			//这里根据实际进行配置
			var point=getCenterCoordinate('content','waitingTip');
			waitingTip.css({'left':point.left,'top':point.top,'display':'block'});
		}else{
			waitingBox.css({'filter':'alpha(opacity=100)','opacity':1});
			waitingTip.css({'display':'none'});
		}
		waiting=!waiting;
	}
	*/
	
	//初始化值
	$loadWaitingTip=$('#loadWaitingTip'); 
	$loadWaitingBox=$('#shadow');

 });

/**
 *    加载MENU页面的阴影框--- #shadow
 *    控制参数 -- bigShadowClosed
 */

var bigShadowClosed=true;
var $loadWaitingBox=null;
var $loadWaitingTip=null;
function doLoadWaiting(){
	if(bigShadowClosed){
		//var pointer=getCenterCoordinate($loadWaitingBox.attr('id'),$loadWaitingTip.attr('id'));
		$loadWaitingBox.css({'filter':'alpha(opacity=60)','opacity':0.6});
		$loadWaitingTip.css({'display':'block'/*,'left':pointer.left,'top':pointer.top*/});
	}else{
		$loadWaitingBox.css({'filter':'alpha(opacity=100)','opacity':1});
		$loadWaitingTip.css('display','none');	
	}	
	bigShadowClosed=!bigShadowClosed;
}

/**
 *   得到小框在大框中的居中位置
 */
function getCenterCoordinate(anchorBoxId,centerBoxId){
	var $anchorBox=$('#'+anchorBoxId);
	var $centerBox=$('#'+centerBoxId);
	var left=$anchorBox.get(0).getBoundingClientRect().left+0.5*($anchorBox.width()-$centerBox.width());
	var top=$anchorBox.get(0).getBoundingClientRect().top+0.5*($anchorBox.height()-$centerBox.height());
	return {'left':left,'top':top};
}

/**
 * 验证阴影是否关闭(时间已经结束)
 */
function validateTabChange(){
	if(bigShadowClosed && subShadowClosed && popWinClosed){
		//continue
	}else{
		alert('请完成操作!')
		return ;
	}
	
}