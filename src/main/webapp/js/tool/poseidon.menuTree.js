/*
 * 	定义树状目录的menus，格式固定，支持两阶
 * 	develop用
 *
 * */
var menuTree={'menus':
			[{'id':'user_all','subMenus':[
								{'id':'user_all_manage','subMenus':null,'root':true},
								{'id':'user_all_add_user','subMenus':null},
								{'id':'user_all_edit_user','subMenus':null}
							]
			  },
			  {'id':'group_all','subMenus':[
			  							{'id':'group_all_manage','subMenus':null,'root':true},
			  							{'id':'group_all_auth_detail','subMenus':null},
			  							{'id':'group_all_user_detail','subMenus':null},
			  							]
			  }],
			  'lastMenu':null
	 };

/**
 * 	设置返回按钮
 */
$(function(){
	$(window.document.body).on('click','.backBtn',function(){
		var id=$(this).closest('.sub_all_inner').attr('id');
		goBack(id);
	});
})


/**
 * 返回上一个窗口
 * @param thisMenuId
 * @return
 */
function goBack(thisMenuId){
	var thisParentMenu=findMainMenu(thisMenuId);
	var lastParentMenu=findMainMenu(menuTree.lastMenu);
	
	//alert('show-last:'+menuTree.lastMenu)
	if(thisParentMenu==lastParentMenu){
		$('#'+thisMenuId).css('display','none');
		$('#'+menuTree.lastMenu).css('display','block');
	}else{
		$('#'+thisParentMenu).css('display','none');
		$('#'+lastParentMenu).css('display','block');
		$('#'+menuTree.lastMenu).siblings('display','none')
					.end().css('display','block');
	}
		menuTree.lastMenu=thisMenuId;
}

/**
 * 显示根子页
 * @param mainMenuId
 * @return
 */
function showRootSubMenu(mainMenuId){
	var menus=menuTree.menus;
	var rootMenuId=(function(){
		for(var i=0;i<menus.length;i++){
			if(menus[i].id==mainMenuId){
				var subMenus=menus[i].subMenus;
				for(var j=0;j<subMenus.length;j++){
					if(subMenus[j].root){
						return subMenus[j].id
					}
				}
			}
		}	
	})();
	
	$('#'+rootMenuId).siblings('.sub_all_inner').css('display','none')
					.end().css('display','block');
}


/**
 * 切换已经加载过的父窗口 -- 还没有使用到，需要将页面缓存加载到这里
 * 暂时使用的是showRootSubMenu方法
 * @param mainMenuId
 * @return
 */
function changeMainMenu(mainMenuId){

	var menus=menuTree.menus;
	var targetRootSubMenu=(function(){
		for(var i=0;i<menus.length;i++){	
			if(menus[i].id==mainMenuId){
				var subMenus=menus[i].subMenus;
				for(var j=0;j<subMenus.length;j++){
					if(subMenus[j].root==true){
						return subMenus[j].id;
					}
				}
			}
		}
	})();
	
	$('.sub_all').css('display','none');
	$('#'+mainMenuId).css('display','block');
	//这里根据实际修改
	$('#'+targetRootSubMenu).siblings('.sub_all_inner').css('display','none')
						.end().css('display','block');
	
}

/**
 * 切换子窗口  
 * @param targetMenuId
 * @param thisMenuId
 * @return
 */					
function changeMenu(targetMenuId,thisMenuId){
	
	var thisParentMenu=findMainMenu(thisMenuId);
	var targetParentMenu=findMainMenu(targetMenuId);
	//处理信息
	if(thisParentMenu==targetParentMenu){
		$('#'+thisMenuId).css('display','none');
		$('#'+targetMenuId).css('display','block');
		
	}else{
		$('#'+thisParentMenu).css('display','none');
		$('#'+targetParentMenu).css('display','block');
		//这里根据实际修改
		$('#'+targetMenuId).siblings('.sub_all_inner').css('display','none')
							.end().css('display','block');
	}
	    menuTree.lastMenu=thisMenuId;	
}

/**
 * 查找父级main页面
 * @param subMenuId
 * @return
 */
function findMainMenu(subMenuId){
	var menus=menuTree.menus;
	for(var i=0;i<menus.length;i++){
		var subMenus=menus[i].subMenus;
		for(var j=0;j<subMenus.length;j++){
			if(subMenuId==subMenus[j].id){
				return menus[i].id;
			} 
		}
	}
}


//*** 应用的状态管理函数!
var appState=(function(){
	
	var state_popWin=false;
	var state_loadingModule=false;
	var state_searching=false;
	
	
	//返回应用的状态
	//出现在任意的悬浮窗口，加载模块中，查询中，锁定app状态为等待，不允许其他的操作!
	var isStateOk=function(){
		return !state_popWin && !state_loadingModule && !state_searching;
	}
	
	//更具String设置app的状态为锁定或者解锁
	var setState=function(stateStr){
		if(stateStr=='popWin'){
			state_popWin=!state_popWin;
		}else if(stateStr=='loadingModule'){
			state_loadingModule=!state_loadingModule;
		}else if(stateStr=='searching'){
			state_searching=!state_searching;
		}
	}

	
	return {
			isStateOk:isStateOk,
			setState:setState
		}
	
})();