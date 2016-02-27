<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<!-- start the middle column -->

<section>

	<h1>Statistics</h1>
	
	<div class="col-wrapper">
		<span class="col col-left"><h1>By Month</h1><canvas id = "canvasId1"></canvas></span>
		<span class="col col-right"><h1>Month Back</h1><canvas id = "canvasId2"></canvas></span>
	</div>
	<script src="barGraph.js"></script>
	<script type ="text/javascript" src="date.js"></script>
	<script>
		var today = new Date();
		var currentYear = today.getFullYear();
		var currentMonth = today.getMonth() + 1;
		
		var graph = new BarGraph(document.getElementById("canvasId1").getContext("2d"));
		graph.margin = 2;
		graph.width = 800;
		graph.height = 400;
		
		var currentMonth = today.add(1 - today.getDate()).days().toString('MM/yyyy');
		var previousMonth = today.add(-1).months().toString('MM/yyyy');
		var twoMonthsBack = today.add(-1).months().toString('MM/yyyy');
		var threeMonthsBack = today.add(-1).months().toString('MM/yyyy');
		var fourMonthsBack = today.add(-1).months().toString('MM/yyyy');
		today = Date.today();
		graph.xAxisLabelArr = [fourMonthsBack, 
		                       threeMonthsBack,
		                       twoMonthsBack,
		                       previousMonth, 
		                       currentMonth];
		graph.update([3, 4, 3, 13, 12]);
		
		var graph2 = new BarGraph(document.getElementById("canvasId2").getContext("2d"));
		graph2.margin = 2;
		graph2.width = 800;
		graph2.height = 400;
		var currentWeek = today.add(-1).weeks().add(1).days().toString('MM/dd/yyyy');
		var previousWeek = today.add(-1).weeks().toString('MM/dd/yyyy');
		var	twoWeeksBack = today.add(-1).weeks().toString('MM/dd/yyyy');
		var threeWeeksBack = today.add(-1).weeks().toString('MM/dd/yyyy');
		var fourWeeksBack = today.add(-1).weeks().toString('MM/dd/yyyy');
		graph2.xAxisLabelArr = [fourWeeksBack,
		                        threeWeeksBack,
		                        twoWeeksBack,
		                        previousWeek,
		                        currentWeek];
		
		graph2.update([3, 4, 3, 13, 12]);
	</script>
</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />