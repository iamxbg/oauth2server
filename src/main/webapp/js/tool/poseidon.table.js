/**
 * 通用的表格生成函数
 * @param data
 * @param columnDefs  -- 提供每行td对应的class定义,特殊的为(行尾)的operation,一般的使用"_XXX"的规范（非强制）
 * @param tableId	  -- table的id
 * @param dataHandler	-- 数据的处理函数
 * @return
 */
function generateOperationTableTr(datas,tableId,dataHandler,colClsDefs){
	var $table=$('#'+tableId);
	$table.find('tr:not(".tableTitle")').remove();
	if(datas.length==0) alert('没有查到记录!');
	for(var i=0;i<datas.length;i++){
		var data=datas[i];
		var $tr=$('<tr>').attr('id',data['id']);
		for(var j=0;j<colClsDefs.length;j++){
			var colCls=colClsDefs[j];
			var $td=$('<td>').addClass(colCls);
				$td=dataHandler(data,colCls,$td);
				$td.appendTo($tr);
		}
		$tr.appendTo($table);
	}
}


/**
 * 
 * @param datas
 * @return
 */

function setPageTool(pageInfo,tableId){
	$pageTool=$('#'+tableId).closest('.searchResult').find('.pageTool');
	$pageTool.find('.inPage').text(pageInfo.inPage)
			.end().find('.pageCount').text(pageInfo.pageCount)
			.end().find('.recordCount').text(pageInfo.recordCount);
	
	var pageSize=pageInfo.pageSize;
			
	
	//设置序号,pageSize需要改进
	$pageTool.closest('.searchResult')
		.find('table').find("tr:not(.tableTitle)").find("td.index")
		.each(function(index,e){
			
			$(this).text((pageInfo.inPage-1)*pageSize+index+1);
			
		});
}

/**********************  自定义配置  *************************************************/
/**
 * 用户的表格生成函数
 * @param data
 * @return
 */
var userTableId='user_searchTable';
var userColClsDefs=['index','_work_id','_rname','_group','_edit_time','operation'];
var userDataHandler=function(data,colCls,$td){
		if(colCls.substr(0,1)=='_'){
			if(colCls.substr(1)=='group'){
				$td.text(data.group.name);
			}else if(colCls.substr(1)=='edit_time'){
				$td.text(computeDate(data.edit_time))
			}else{
				$td.text(data[colCls.substr(1)]);
			}
		}else if(colCls=='operation'){
			if(data.del_flag=='N'){
				var operationTypes=['editBtn','deleteBtn'];
				for(var k=0;k<operationTypes.length;k++){
					var operationBtn=$('<div class="oprBtn">').addClass(operationTypes[k]);
					if(operationTypes[k]=='editBtn') operationBtn.text('编辑');
					else if(operationTypes[k]=='deleteBtn') operationBtn.text('删除');
					$td.append(operationBtn);
				}
			}else if(data.del_flag=='Y'){
					var operationBtn=$('<div class="oprBtn">').addClass('activeBtn');
						operationBtn.text('启用');
					var destroyBtn=$('<div class="oprBtn">').addClass('destroyBtn');
						destroyBtn.text('销毁');
					$td.append(operationBtn).append(destroyBtn);
			}
			
		}
		return $td;
}

/*普通的生成数据的*/
function generateUserTr(datas){
	if(datas==undefined) alert('debug:user datas is undeinfed')
	generateOperationTableTr(datas,userTableId,userDataHandler,userColClsDefs);
}
/*处理页面的--对普通的再次进行封存*/
function generateUserPage(pageData){
	var pageInfo=pageData;
	var datas=pageData.dataList;
	
	generateUserTr(datas);
	setPageTool(pageInfo,userTableId);
}





/**
 * 	新闻的表格生成函数	 
 * 
 */
var newsTableId='news_searchTable';
var newsColClsDefs=['index','_title','_publish_date','_editor','operation'];
var newsDataHandler=function(data,colCls,$td){
		if(colCls.substr(0,1)=='_'){
			if(colCls=='_publish_date') $td.text(computeDate(data[colCls.substr(1)]))
				else $td.text(data[colCls.substr(1)]);
		}else if(colCls=='operation'){
			var operationTypes;
				if(data['del_flag']=='N') operationTypes=['editBtn','deleteBtn'];
				else if(data['del_flag']=='Y') operationTypes=['activeBtn'];
				
			for(var k=0;k<operationTypes.length;k++){
				var operationBtn=$('<div class="oprBtn">').addClass(operationTypes[k]);
				if(operationTypes[k]=='editBtn') operationBtn.text('编辑');
				else if(operationTypes[k]=='deleteBtn') operationBtn.text('删除');
				else if(operationTypes[k]=='activeBtn') operationBtn.text('恢复');
				$td.append(operationBtn);
			}
		}
		return $td;
}

function generateNewsTr(datas){
	generateOperationTableTr(datas,newsTableId,newsDataHandler,newsColClsDefs);
}
function generateNewsPage(pageData){
	var pageInfo=pageData
	var datas=pageData.dataList;
	generateNewsTr(datas);
	setPageTool(pageInfo,newsTableId);
}


/**
 * 表格的表格生成函数
 * @param data
 * @return
 */
var tableTableId='table_searchTable';
var tableColClsDefs=['_edit_time','_name','_type','_editor','operation'];
var tableDataHandler=function(data,colCls,$td){
		if(colCls.substr(0,1)=='_'){
			if(colCls=='_edit_time'){
				var edit_time=computeDate(data['edit_time'])
				$td.text(edit_time);
			}else if(colCls=='_type'){
				if(data['type']){
					//$td.text(data['type']);
					$td.append($('<span>').text(data['type']));
					$td.append($('<a href="#" class="changeBindType" style="float:right;">').text('切换'))
				}else {
					$a=$('<a href="#" class="bindTableType">').text('未绑定')
					$td.append($a);
				}
			}else{
				$td.text(data[colCls.substr(1)]);
			}
				
		}else if(colCls=='operation' && data['del_flag']=='N'){
			var operationTypes=['deleteBtn'];
			for(var k=0;k<operationTypes.length;k++){
				var operationBtn=$('<div class="oprBtn">').addClass(operationTypes[k]);
				if(operationTypes[k]=='deleteBtn') operationBtn.text("删除");
				//else if(operationTypes[k]=='downloadBtn') operationBtn.text("下载");
				$td.append(operationBtn);
			}
		}
		return $td;
}


/*普通的生成数据的*/
function generateTableTr(datas){
	if(datas==undefined) alert('debug:Table datas is undeinfed')
	generateOperationTableTr(datas,tableTableId,tableDataHandler,tableColClsDefs);
}
/*处理页面的--对普通的再次进行封存*/
function generateTablePage(pageData){
	var pageInfo=pageData;
	var datas=pageData.dataList;
	generateTableTr(datas);
	setPageTool(pageInfo,tableTableId);
}



/**
 * 生成日志的表单的表头
 * */
var area_a_log_title={'titleDefs':[
	   	{'cls':'index','name':'序号'},
	   	{'cls':'_apply_date','name':'申请日期','editable':true},
	   	{'cls':'_apply_department','name':'申请部门','editable':true},   	
	   	{'cls':'_invoice_no','name':'INVOICE_NO','editable':true},
	   	{'cls':'_pre_apply_no','name':'预申请单号','editable':true},
	   	{'cls':'_supplier','name':'供货方','editable':true},
	   	{'cls':'_goods_name','name':'物品名称','editable':true},
		{'cls':'_pallet_count','name':'展板数','editable':true},
		{'cls':'_package_count','name':'包数','editable':true},
		{'cls':'_count','name':'数量','editable':true},
		{'cls':'_plate_num','name':'车牌号','editable':true},
		{'cls':'_customs_broker_out','name':'报关行(报出)','editable':true},
		{'cls':'_customs_broker_in','name':'报关行(报进)','editable':true},
		{'cls':'_applicant','name':'申请人','editable':true},
		{'cls':'_purchasing_agent','name':'采购','editable':true},
		{'cls':'_truker_phone','name':'件数','editable':true},
	   	{'cls':'_comment','name':'备注','editable':true},
	   	{'cls':'_is_arrived','name':'车辆状态','editable':true},
		{'cls':'_process_status_id','name':'通关状态','editable':true},
	   	{'cls':'_opt_version','name':'版本','editable':true},
		{'cls':'_last_editor','name':'编辑者','editable':true},
		{'cls':'_edit_time','name':'编辑时间','editable':true}
	   ]};




var area_c_log_title={'titleDefs':[		
		{'cls':'index','name':'序号'},
		{'cls':'_apply_date','name':'申请日期','editable':true},
		{'cls':'_apply_department','name':'申请部门','editable':true},
		{'cls':'_invoice_no','name':'INVOICE_NO','editable':true},
		{'cls':'_pre_apply_no','name':'预审请单号','editable':true},
		{'cls':'_goods_name','name':'物品名称','editable':true},
		{'cls':'_package_count','name':'包数','editable':true},
		{'cls':'_count','name':'数量','editable':true},
		{'cls':'_plate_num','name':'车牌号','editable':true},
		{'cls':'_truker_phone','name':'司机电话','editable':true},
		{'cls':'_customs_broker_out','name':'报关行(出)','editable':true},
		{'cls':'_customs_broker_in','name':'报关行(进)','editable':true},
		{'cls':'_applicant','name':'申请人','editable':true},
		{'cls':'_purchasing_agent','name':'采购','editable':true},
		{'cls':'_comment','name':'备注','editable':true},
		{'cls':'_is_arrived','name':'车辆状态','editable':true},
		{'cls':'_process_status_id','name':'通关状态','editable':true},
		{'cls':'_opt_version','name':'版本','editable':true},
		{'cls':'_last_editor','name':'编辑人','editable':true},
		{'cls':'_edit_time','name':'编辑时间','editable':true}
		]
	   };	

function generateLogTitle_A(){
	//这里需要更具实际配置
	//移除旧的标题和列宽的配置
	var $table=$('#log_searchTable');
	$('.tableTitle',$table).remove();
	$('col',$table).remove();
	//生成标题行
		var colTitle=area_a_log_title;;
		var $tr=$('<tr class="tableTitle">');

		for(var i=0;i<colTitle.titleDefs.length;i++){
			var colDef=colTitle.titleDefs[i];
			var $col=$('<col>').addClass(colDef['cls']);
			    $col.appendTo($table);
			   var $td=$('<th>').addClass(colDef['cls']).text(colDef['name']);

			   $tr.append($td);
		}
		$tr.prependTo($table);	
}

function generateLogTitle_C(){
	//这里需要更具实际配置
	//移除旧的标题和列宽的配置
	var $table=$('#log_searchTable');
	$('.tableTitle',$table).remove();
	$('col',$table).remove();
	//生成标题行
		var colTitle=area_c_log_title;
		var $tr=$('<tr class="tableTitle">');
		
		for(var i=0;i<colTitle.titleDefs.length;i++){
			var colDef=colTitle.titleDefs[i];
			var $col=$('<col>').addClass(colDef['cls']);
			    $col.appendTo($table);
			   var $td=$('<th>').addClass(colDef['cls']).text(colDef['name']);
			   
			   $tr.append($td);
		}
		$tr.prependTo($table);	
}


var	logColClsDef_C=['index','_apply_date','_apply_department','_invoice_no','_pre_apply_no','_goods_name','_package_count','_count',
   	                '_plate_num','_truker_phone','_customs_broker_out','_customs_broker_in','_applicant','_purchasing_agent','_comment','_is_arrived','_process_status_id',
   	               '_opt_version','_last_editor','_edit_time'];
										
var	logColClsDef_A=['index','_apply_date','_apply_department','_invoice_no','_pre_apply_no','_supplier','_goods_name','_pallet_count','_package_count','_count',
                    '_plate_num','_customs_broker_out','_customs_broker_in','_applicant','_purchasing_agent','_truker_phone','_comment','_is_arrived','_process_status_id',
                    '_opt_version','_last_editor','_edit_time'];


var logTableId='log_searchTable';

var logDataHandler=function(data,colCls,$td){
		if(colCls.substr(0,1)=='_'){
			var name=colCls.substr(1);
			if(name=='edit_time' ){
				$td.text(computeTimestamp(data.edit_time))
			}else if(name=="apply_date"){
				$td.text(computeDate(data.apply_date))
			}else if(name=="is_arrived"){
				if(data.is_arrived=='Y') $td.text("已抵达");
				else if(data.is_arrived=='N') $td.text("未抵达");
			}else if(name=="process_status_id"){
				var psId=data.process_status_id;
				switch(psId){
					case 1:$td.text('卡口接收电子运抵报告申请单');break;
					case 2:$td.text('卡口接收白卡本');break;
					case 3:$td.text('卡口本及报关资料交中央关务');break;
					case 4:$td.text('报关行申报中');break;
					case 5:$td.text('报关行申报完成');break;
					case 6:$td.text('报关行申报异常');break;
					case 7:$td.text('商检审核中');break;
					case 8:$td.text('商检审核异常');break;
					case 9:$td.text('商检查验中');break;
					case 10:$td.text('商检查验异常');break;
					case 11:$td.text('海关审核中');break;
					case 12:$td.text('海关审核异常');break;
					case 13:$td.text('海关查验中');break;
					case 14:$td.text('海关查验异常');break;
					case 15:$td.text('货车放行');break;
					case 16:$td.text('关务申报中');break;
					case 17:$td.text('关务申报完成');break;
					case 18:$td.text('关务申报异常');break;
					case 19:$td.text('核放单捆绑中');break;
					case 20:$td.text('核放单捆绑异常');break;
				}

			}else{
				$td.text(data[colCls.substr(1)]);
			}
		}
		return $td;
}

function generateLogTr_A(datas){
	generateOperationTableTr(datas,logTableId,logDataHandler,logColClsDef_A);
}
function generateLogTr_C(datas){
	generateOperationTableTr(datas,logTableId,logDataHandler,logColClsDef_C);
}

function generateLogPage_A(pageData){
	var pageInfo=pageData;
	var datas=pageData.dataList;
	generateLogTitle_A(datas);
	generateLogTr_A(datas);
	setPageTool(pageInfo,logTableId);
}

function generateLogPage_C(pageData){
	var pageInfo=pageData;
	var datas=pageData.dataList;
	generateLogTitle_C(datas);
	generateLogTr_C(datas);
	setPageTool(pageInfo,logTableId);
}

/**
 * 元数据的生成函数
 * @param data
 * @return
 */
var metainfoTableId='metainfo_searchTable';
var metainfoColClsDefs=['index','_name','_editor','_edit_time','operation']; 
var metainfoDataHandler=function(data,colCls,$td){
		if(colCls.substr(0,1)=='_'){
			if(colCls.substr(1)=='edit_time'){
				$td.text(computeDate(data['edit_time']))
			}else{
				$td.text(data[colCls.substr(1)]);
			}
		}else if(colCls=='operation'){
			var del_flag=data['del_flag'];
			var operationTypes;
			if(del_flag=='N') operationTypes=['deleteBtn'];
			else if(del_flag=='Y') operationTypes=['activeBtn'];
			//var operationTypes=['deleteBtn'];
			for(var k=0;k<operationTypes.length;k++){
				var operationBtn=$('<div class="oprBtn">').addClass(operationTypes[k]);
				if(operationTypes[k]=='deleteBtn') operationBtn.text('删除');
				else if(operationTypes[k]=='activeBtn') operationBtn.text('激活');
				$td.append(operationBtn);
			}
		}
		return $td;
}

function generateMetainfoTr(datas){
	if(datas==undefined) alert('debug:metainfo datas is undeinfed')
	generateOperationTableTr(datas,metainfoTableId,metainfoDataHandler,metainfoColClsDefs);
}

function generateMetainfoPage(pageData){
	var pageInfo=pageData;
	var datas=pageData.dataList;
	generateMetainfoTr(datas);
	setPageTool(pageInfo,metainfoTableId);
}

/**
 * 表单类型的生成函数
 * @param data
 * @return
 */
var tableTypeTableId='tableType_searchTable';
var tableTypeColClsDefs=['index','_name','_editor','_edit_time','operation']; 
var tableTypeDataHandler=function(data,colCls,$td){
		if(colCls.substr(0,1)=='_'){
			if(colCls.substr(1)=='edit_time'){
				$td.text(computeDate(data['edit_time']))
			}else{
				$td.text(data[colCls.substr(1)]);
			}
		}else if(colCls=='operation'){

				var operationBtn=$('<div class="oprBtn">');
				if(data['del_flag']=='Y'){
					operationBtn.addClass('activeBtn').text('激活');
				}else if(data['del_flag']=='N'){
					operationBtn.addClass('deleteBtn').text('删除');
				}
				$td.append(operationBtn);
			}
		
		return $td;
}

function generateTableTypeTr(datas){
	if(datas==undefined) alert('debug:tableType datas is undeinfed')
	generateOperationTableTr(datas,tableTypeTableId,tableTypeDataHandler,tableTypeColClsDefs);
}

function generateTableTypePage(pageData){
	var pageInfo=pageData;
	var datas=pageData.dataList;
	generateTableTypeTr(datas);
	setPageTool(pageInfo,tableTypeTableId);
}



/**
 * 操作日志的生成函数
 * @param data
 * @return
 */
var oprLogTableId='oprLog_searchTable';
var oprLogColClsDefs=['index','_operation_type','_work_id','_edit_time','_comment']; 
var oprLogDataHandler=function(data,colCls,$td){
		if(colCls.substr(0,1)=='_'){
			var colName=colCls.substr(1);
			if(colName=='edit_time'){
				$td.text(computeTimestamp(data['edit_time']))
			}else{
				$td.text(data[colCls.substr(1)]);
			}
		}
		return $td;
}

function generateOprLogTr(datas){
	if(datas==undefined) alert('debug:oprLog datas is undeinfed')
	generateOperationTableTr(datas,oprLogTableId,oprLogDataHandler,oprLogColClsDefs);
}

function generateOprLogPage(pageData){
	var pageInfo=pageData;
	var datas=pageData.dataList;
	generateOprLogTr(datas);
	setPageTool(pageInfo,oprLogTableId);
}


/**
 * 操作 Email 的生成函数
 * @param data
 * @return
 */
var mailTableId='mail_searchTable';
var mailColClsDefs=['index','_pre_apply_no','_sender_work_id','_sender_name','_send_date','_mail_to','_type','_checked','operation']; 
var mailDataHandler=function(data,colCls,$td){
		if(colCls.substr(0,1)=='_'){
			var colName=colCls.substr(1);
			if(colName=='send_date'){
				$td.text(computeTimestamp(data['send_date']))
			}else if(colName=='type'){
				var type=data['type'];
				if(type=='update'){
					$td.text('修改');
				}else if(type=='delete'){
					$td.text('删除');
				}
			}else{
				$td.text(data[colCls.substr(1)]);
				if(colName=='checked'){
					if(data['checked']=='Y'){
						$td.text('已处理')
						$td.css('background','#00FF66')
					}else if(data['checked']=='N'){
						$td.text('未处理')
						$td.css('background','#FFFF99')
					} 
				}
			}
		}else if(colCls=='operation'){

			var operationBtn=$('<div class="oprBtn">');
			
			if(data['checked']=='Y'){
				operationBtn.addClass('deleteBtn').text('删除记录');
			}else if(data['checked']=='N'){
				operationBtn.addClass('deleteBtn').text('撤销请求');
			}
			$td.append(operationBtn);
		}
		return $td;
}

function generateMailTr(datas){
	if(datas==undefined) alert('debug:email datas is undeinfed')
	generateOperationTableTr(datas,mailTableId,mailDataHandler,mailColClsDefs);
}

function generateMailPage(pageData){
	var pageInfo=pageData;
	var datas=pageData.dataList;
	generateMailTr(datas);
	setPageTool(pageInfo,mailTableId);
}

/**
 * 关务集中管理未处理的邮件
 * @param data
 * @return
 */

var mailBoxTableId='mailBox_searchTable';
var mailBoxColClsDefs=['index','_pre_apply_no','_sender_work_id','_sender_name','_send_date','_mail_to','_type','_checked','operation']; 
var mailBoxDataHandler=function(data,colCls,$td){
		if(colCls.substr(0,1)=='_'){
			var colName=colCls.substr(1);
			if(colName=='send_date'){
				$td.text(computeTimestamp(data['send_date']))
			}else if(colName=='type'){
				if('delete'==data['type']){
					$td.text('删除');
					$td.css({'background':'#FF7F7F','letter-spacing':'4px'})
				}else if('update'==data['type']){
					$td.text('修改');
					$td.css('background','#FFFF99');
					$td.css({'background':'#FF5656','letter-spacing':'4px'})
				}
			}else{
				$td.text(data[colCls.substr(1)]);
				if(colName=='checked'){
					if(data['checked']=='Y') $td.css('background','#00FF66')
					else if(data['checked']=='N') $td.css('background','#FFFF99')
				}
			}
		}else if(colCls=='operation'){

			var operationBtn=$('<div class="oprBtn">');
			
			if(data['checked']=='Y'){
				operationBtn.addClass('rogerBtn').text('删除');
			}else if(data['checked']=='N'){
				operationBtn.addClass('infoBtn').text('详情');
			}
			$td.append(operationBtn);
		}else if(colCls=='index'){
			$td.data('sn_code',data['sn_code']);
		}
		return $td;
}

function generateMailBoxTr(datas){
	if(datas==undefined) alert('debug:mailBox datas is undeinfed')
	generateOperationTableTr(datas,mailBoxTableId,mailBoxDataHandler,mailBoxColClsDefs);
}

function generateMailBoxPage(pageData){
	var pageInfo=pageData;
	var datas=pageData.dataList;
	generateMailBoxTr(datas);
	setPageTool(pageInfo,mailBoxTableId);
}
