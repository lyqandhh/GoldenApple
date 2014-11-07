function pages(count,pageCount,currentPage,form) {
	if(pageCount >= count) return

	var htmlStr = "";
	var countPageNum = Math.ceil(count / pageCount);
	htmlStr += '<div class="ta-c"><ul class="pagination" style="margin:0;">';
	for(var i=1; i<=countPageNum; i++) {
		if(i == currentPage) {
			htmlStr += '<li class="active"><a href="javascript:go('+i+',\''+form+'\')">'+i+' <span class="sr-only">(current)</span></a></li>';
		}else {
			htmlStr += '<li><a href="javascript:go('+i+',\''+form+'\')">'+i+'</a></li>';
		}
	}
	htmlStr += '</ul></div>';
	document.write(htmlStr);
}

function go(page,form) {
	$("#page").val(page);
	$("#"+form).submit();
}