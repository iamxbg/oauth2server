/***
 *   readme:
 *   	1.jqery-1.11.3

/**
 * 	根据action计算对应的正确页数
 * 	action应该是prev,next,first,last,number 4中
 * 	null值对应无操作
 */
function computePageToGo(pageNow,totalPage,action,toPage){

	if(action=='prev'){
		return pageNow==1?null:pageNow-1;
	}else if(action=='next'){
		return pageNow==totalPage?null:pageNow+1;
	}else if(action=='first'){
		return pageNow==1?null:1;
	}else if(action=='last'){
		return pageNow==totalPage?null:totalPage;
	}else if(action=='number' && toPage!=""){
		//添加正则
		if(toPage>totalPage){
			return null;
		}else if(toPage!=undefined && toPage!=null 
			/*添加正则*/ && /^d+$/.test(toPage)){
			alert('debug:数字格式不正确!')
			return null;
		}else if(toPage<1 || toPage>totalPage){
			alert('debug:页数不正确');
			return null;
		}else{
			return toPage;
		}
	}else alert("DEBUG:fn computePageToGo() -- action not right")

}


/**
 *  跳页通用函数
 *  	注意:也后台衔接的固定参数: pageToSearch
 */
function searchPage(url,params,sucFunc,errFunc,compFunc){
	$.ajax({
		url:url,
		type:'POST',
		contentType:'application/json',
		dataType:'json',
		data:params,
		timeout:5000,
		complete:function(xhr){
			xhr.done(function(data){
				sucFunc(data);
			}),
			xhr.fail(function(jqXHR){
				decodeErrXHR(jqXHR);
			})
		}
		
		/*
		success:function(data){
			if(data.recordCount==0)alert('没有记录!')

			sucFunc(data);
		},
		error:function(){errFunc!=undefined?errFunc():null},
		complete:function(){compFunc!=undefined?compFunc():null}*/
	});
}

/********************************  自定义配置部分   *************************************************/
/*
 * search_page_XXX方程的形式是类似的，在调用它的页面上定义（而非初始化）url,
 * 一般定义为 XXX/search(非强制),每次在调用前重载一次params参数即可（这里参数选择的是重载，
 * 也可以自己修改为传入的方式）
 * 
 * 		
 */

/**
 * 
 * @param url   -- 需要在页面定义的url
 * @param pageToSeach
 * @param params  -- 收集参数的方程，最好定义在页面，方便修改
 * @return
 */

function searchPage_user(pageInfo,params){
	var sucFunc=generateUserPage;
	var url=searchPage_user_url+"/"+pageInfo.inPage;
	searchPage(url,params,sucFunc/*,errFunc,compFunc*/);
}

/**
 * news的页面查询函数
 * @param pageToSearch
 * @param params 	--调用函数之前先重载params
 * @return
 */
function searchPage_news(pageInfo,params){
	var sucFunc=generateNewsPage;
	var url=searchPage_news_url+"/"+pageInfo.inPage;
	searchPage(url,params,sucFunc/*,errFunc,compFunc*/);
}

/**
 *  table 页面查询
 * @param pageInfo
 * @param params
 * @return
 */
function searchPage_table(pageInfo,params){
	var sucFunc=generateTablePage;
	var url=searchPage_table_url+"/"+pageInfo.inPage;
	searchPage(url,params,sucFunc/*,errFunc,compFunc*/);
}

/**
 *  FORM 页面查询
 * @param pageInfo
 * @param params
 * @return
 */
function searchPage_form(pageInfo,params){
	var sucFunc;

	var area=$('#formSearchParams select[name="area"]').val();

	if(area=='A'){
		sucFunc=generateFormPage_A;
	}else if(area=='C'){
		sucFunc=generateFormPage_C;
	}
	
	var url=searchPage_form_url+"/"+pageInfo.inPage;
	searchPage(url,params,sucFunc/*,errFunc,compFunc*/);
}


/**
 *  ProcessingLog 页面查询
 * @param pageInfo
 * @param params
 * @return
 */

function searchPage_log(pageInfo,params){
	var sucFunc;

	var area=$('#logSearchParams select[name="area"]').val();
	
	if(area=='A'){
		sucFunc=generateLogPage_A;
	}else if(area=='C'){
		sucFunc=generateLogPage_C;
	}
	
	var url=searchPage_log_url+"/"+pageInfo.inPage;
	searchPage(url,params,sucFunc/*,errFunc,compFunc*/);
}

/**
 *  元数据页面查询
 * @param pageInfo
 * @param params
 * @return
 */

function searchPage_metainfo(pageInfo,params){
	var sucFunc=generateMetainfoPage;
	var url=searchPage_metainfo_url+"/"+pageInfo.inPage;
	searchPage(url,params,sucFunc/*,errFunc,compFunc*/);
}

/**
 *  表单类型页面查询
 * @param pageInfo
 * @param params
 * @return
 */

function searchPage_tableType(pageInfo,params){
	var sucFunc=generateTableTypePage;
	var url=searchPage_tableType_url+"/"+pageInfo.inPage;
	searchPage(url,params,sucFunc/*,errFunc,compFunc*/);
}


/**
 *  操作日志页面查询
 * @param pageInfo
 * @param params
 * @return
 */

function searchPage_oprLog(pageInfo,params){
	var sucFunc=generateOprLogPage;
	var url=searchPage_oprLog_url+"/"+pageInfo.inPage;
	searchPage(url,params,sucFunc/*,errFunc,compFunc*/);
}


/**
 *  管理邮件的页面查询
 * @param pageInfo
 * @param params
 * @return
 */

function searchPage_mail(pageInfo,params){
	var sucFunc=generateMailPage;
	var url=searchPage_mail_url+"/"+pageInfo.inPage;
	searchPage(url,params,sucFunc/*,errFunc,compFunc*/);
}

/**
 *  关务为处理邮件汇总
 * @param pageInfo
 * @param params
 * @return
 */

function searchPage_mailBox(pageInfo,params){
	var sucFunc=generateMailBoxPage;
	var url=searchPage_mailBox_url+"/"+pageInfo.inPage;
	searchPage(url,params,sucFunc/*,errFunc,compFunc*/);
}

