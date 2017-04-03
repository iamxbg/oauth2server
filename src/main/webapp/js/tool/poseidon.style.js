//按钮的颜色函数

$(function(){
	//表格中按钮的css调节
	
	$(document).on('mouseenter','.oprBtn,.search,.add',function(e){
		this.style.cursor='hand'

	}).on('mouseenter','.backBtn',function(e){
		if(appState.isStateOk())
		$(e.target).css('background','skyblue');
	}).on('mouseleave','.backBtn',function(e){
		if(appState.isStateOk())
		$(e.target).css('background','lightblue')
	}).on('mouseenter','.popWinBtn',function(e){
		if(appState.isStateOk())
		$(e.target).css('background','skyblue');
	}).on('mouseleave','.popWinBtn',function(e){
		if(appState.isStateOk())
		$(e.target).css('background','#fff')
	}).on('mouseenter','.edit_details',function(e){
		if(appState.isStateOk())
		$(e.target).css('background','skyblue')
	}).on('mouseleave','.edit_details',function(e){
		if(appState.isStateOk())
		$(e.target).css('background','lightblue')
	})
	
	//左侧导航栏css
	$('.menu').on('mouseenter',function(){
		if(appState.isStateOk()){
			if(($(this).attr('id').split('_')[1]+'_all')!=$('.menuBox').data('openning'))
				$(this).css('background','#ddd')
		}
	}).on('mouseleave',function(){
		if(appState.isStateOk()){
			if(($(this).attr('id').split('_')[1]+'_all')!=$('.menuBox').data('openning'))
				$(this).css('background','#fff')	
		}
	})
	
});	



	
	
	

