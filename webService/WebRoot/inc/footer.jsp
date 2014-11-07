<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
	<script type="text/javascript">
		$(function() {
			var menuActive = $("#menuActive").val();
			if(menuActive == 1) {
				$("#menuManage").addClass("active");
			}else if(menuActive == 2) {
				$("#menuTongbaoAudit").addClass("active");
			}else if(menuActive == 3) {
				$("#menuManageCompany").addClass("active");
			}else if(menuActive == 4) {
				$("#menuManageService").addClass("active");
			}else if(menuActive == 5) {
				$("#menuManagePush").addClass("active");
			}else if(menuActive == 6) {
				$("#menuManageTongbaoka").addClass("active");
			}
		});
	</script>
  </body>
</html>