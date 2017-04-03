/**
 *   初始化子页面的函数
 */
function init_sub(sub_all_obj){
	$(sub_all_obj).css({'height':$('#content').height()}); //减去2*(border 1px  + padding 2px )
	$(sub_all_obj).find('.sub_all_inner').css('width',$('#content').width()*0.97);
	//设置shadowBOx
	$('.sub_shadow').css('width',$('#content').width()*0.97);
}