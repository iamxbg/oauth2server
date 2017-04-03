
var PAGE=null;

$(function(){

	
	/**
	 *  重新查询或刷新页面
	 */
	$(window.document.body).on('click','.search',function(e){
		//检查状态!
		if(appState.isStateOk()){

			var $searchParams=$(this).closest('.searchParams');
			var pageToolId=$searchParams.next('.searchResult').find('.pageTool').attr('id');
			var searchParams=null;
			var searchHandler=null;
			
				if(pageToolId=='pageTool_user'){
					searchHandler=searchPage_user;
					searchParams=getUserParams_fresh();
				}else if(pageToolId=='pageTool_news'){
					searchHandler=searchPage_news;
					searchParams=getNewsParams_fresh();
				}else if(pageToolId=='pageTool_table'){
					searchHandler=searchPage_table;
					searchParams=getTableParams_fresh();
				}else if(pageToolId=='pageTool_form'){
					searchHandler=searchPage_form;
					searchParams=getFormParams_fresh();
					
				}else if(pageToolId=='pageTool_log'){
					searchHandler=searchPage_log;
					searchParams=getLogParams_fresh();
				}else if(pageToolId=='pageTool_metainfo'){
					searchHandler=searchPage_metainfo;
					searchParams=getMetainfoParams_fresh();
				}else if(pageToolId=='pageTool_tableType'){
					searchHandler=searchPage_tableType;
					searchParams=getTableTypeParams_fresh();
				}else if(pageToolId=='pageTool_oprLog'){
					searchHandler=searchPage_oprLog;
					searchParams=getOprLogParams_fresh();
				}else if(pageToolId=='pageTool_mail'){
					searchHandler=searchPage_mail;
					searchParams=getMailParams_fresh();
				}else if(pageToolId=='pageTool_mailBox'){
					searchHandler=searchPage_mailBox;
					searchParams=getMailBoxParams_fresh();
				}
				else{
					alert('debug: poseidon.js-- page ID:'+pageToolId+'不存在')
				}
				var pageInfo={'inPage':1};
				//查询
				if(searchParams!=null){
					searchHandler(pageInfo,searchParams);
				}
					
				
		}else{
			alert('info: 请先完成操作!')
		}
	});
	
	
	/**
	 *  翻页的相关函数
	 *   翻页的组件需要自己在页面上定义，翻页按钮（类）包括了（可选）（first,last,prev,next,number）
	 *   
	 *   约定： 1.翻页条为.pageTool
	 *   		2.自定义.pageTool的id,并添加到下面的二元表达式中
	 */
	$(window.document.body,'.pageTool').on('click','.pageTool_button',function(){
		
		if(appState.isStateOk()){
			
			
			var $pageTool=$(this).closest('.pageTool');
			/*获取组件的值*/
			var inPage=$.trim($pageTool.find('.inPage').text());
			var toPage=$.trim($pageTool.find('.toPage').val());
			var pageCount=$.trim($pageTool.find('.pageCount').text());
				var recordCount=$.trim($pageTool.find('.recordCount').text());
				/**这里需要正则匹配*/
				if(inPage==null || inPage==""){
					alert("Debug -- inPage null --should not happend");
					return ;
				}
				
				toPage=toPage==""?"":parseInt(toPage);
				inPage=parseInt(inPage);
				pageCount=parseInt(pageCount);
				recordCount=parseInt(recordCount);

			var action=null;
			//--有优化的空间 
			if($(this).hasClass('prev')) action='prev';
			else if($(this).hasClass('next')) action='next';
			else if($(this).hasClass('first')) action='first';
			else if($(this).hasClass('last')) action='last';
			else if($(this).hasClass('number') && toPage!="" && !(toPage!=toPage) ) action='number';

			if(action==null){ alert('debug:2345')}
			
			
				//构建页面对象
				var pageToGo=computePageToGo(inPage,pageCount,action,toPage);
				var pageInfo={};
		   		pageInfo.inPage=inPage;
		   		pageInfo.pageCount=pageCount;
		   		pageInfo.recordCount=recordCount;
		   		

			if(pageToGo!=null){	
				pageInfo.inPage=pageToGo;
				
				/*选择searchHandler和参数方程*/
				var pageToolId=$pageTool.attr('id');
				var searchParams=null;
				var searchHandler=null;
				if(pageToolId=='pageTool_user'){
					searchHandler=searchPage_user;
					searchParams=getUserParams_old();
				}else if(pageToolId=='pageTool_news'){
					searchHandler=searchPage_news;
					searchParams=getNewsParams_old();
				}else if(pageToolId=='pageTool_table'){
					searchHandler=searchPage_table;
					searchParams=getTableParams_old();
				}else if(pageToolId=='pageTool_form'){
					searchParams=getFormParams_old();
					searchHandler=searchPage_form;
				//debug的代码	
				}else if(pageToolId=='pageTool_log'){
					searchParams=getLogParams_old();
					searchHandler=searchPage_log;
				}else if(pageToolId=='pageTool_metainfo'){
					searchParams=getMetainfoParams_old();
					searchHandler=searchPage_metainfo;
				}else if(pageToolId=='pageTool_tableType'){
					searchHandler=searchPage_tableType;
					searchParams=getTableTypeParams_old();
				}else if(pageToolId=='pageTool_oprLog'){
					searchHandler=searchPage_oprLog;
					searchParams=getOprLogParams_old();
				}else if(pageToolId=='pageTool_mail'){
					searchHandler=searchPage_mail;
					searchParams=getMailParams_old();
				}else if(pageToolId=='pageTool_mailBox'){
					searchHandler=searchPage_mailBox;
					searchParams=getMailBoxParams_old();
				}else{
					alert('debug: poseidon.js-- page ID不存在')
				}
				if(searchHandler==null){
					alert('debug:searchHandler为空,不应该发生!')
					return;
				}

				searchHandler(pageInfo,searchParams);
			}else{
				//alert('debug:不查询，页数有误')
			};
			
		}

	});
	

});














