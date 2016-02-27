<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />
<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>
<%@ page import="finalproject.data.transactionDB" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import= "java.text.SimpleDateFormat"%>
<%@ page import= "java.util.Calendar"%>
<%@ page import= "java.util.ArrayList"%>
<!-- start the middle column -->

<section>

	<h1>Statistics</h1>
	
	<div class="col-wrapper">
		<span class="col col-left"><h1>By Month</h1><canvas id = "canvasId1"></canvas></span>
		<span class="col col-right"><h1>Month Back</h1><canvas id = "canvasId2"></canvas></span>
	</div>
	<script src="barGraph.js"></script>
	<script type ="text/javascript" src="date.js"></script>
	<%
		ArrayList<String> monthArrayValues = new ArrayList<String>();
		ArrayList<Double> weekArrayValues = new ArrayList<Double>();
		ArrayList<Date> dMonthArray = new ArrayList<Date>();
		ArrayList<Date> dWeekArray = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		DateFormat monthDateFormat = new SimpleDateFormat("MM/yyyy");
		DateFormat weekDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		cal.set(Calendar.DATE, 1);
		monthArrayValues.add(monthDateFormat.format(cal.getTime()));
		dMonthArray.add(cal.getTime());
		for(int i = 0; i < 4; i++)
		{
			cal.add(Calendar.MONTH, -1);
			monthArrayValues.add(monthDateFormat.format(cal.getTime()));
			dMonthArray.add(cal.getTime());
		}
		System.out.println(monthArrayValues);
		cal = Calendar.getInstance();
		
		cal.set(Calendar.DAY_OF_WEEK, 0);
		dWeekArray.add(cal.getTime());
		for(int i = 0; i < 4; i++)
		{
			cal.set(Calendar.WEEK_OF_YEAR, -1);
			dWeekArray.add(cal.getTime());
		}
		System.out.println(dMonthArray.toString());
		StringSet set = transactionDB.getTotalSalesForListOfCalendarMonths(dMonthArray);
		if(set == null)
			return;
	%>
	<script>
		var today = new Date();
		var currentYear = today.getFullYear();
		var currentMonth = today.getMonth() + 1;
		
		var graph = new BarGraph(document.getElementById("canvasId1").getContext("2d"));
		graph.margin = 2;
		graph.width = 800;
		graph.height = 400;
		
		var currentMonth = "<%=monthArrayValues.get(0)%>"
		var previousMonth = "<%=monthArrayValues.get(1)%>"
		var twoMonthsBack = "<%=monthArrayValues.get(2)%>"
		var threeMonthsBack = "<%=monthArrayValues.get(3)%>"
		var fourMonthsBack = "<%=monthArrayValues.get(4)%>"
		graph.xAxisLabelArr = [fourMonthsBack, 
		                       threeMonthsBack,
		                       twoMonthsBack,
		                       previousMonth, 
		                       currentMonth];
		
		graph.update([
		              <%=set.getFourValuesBack()%>,
		              <%=set.getThreeValuesBack()%>,
		              <%=set.getTwoValuesBack()%>,
		              <%=set.getPreviousValue()%>,
		              <%=set.getCurrentValue()%>
		              ]);
		
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
	</script>
</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />