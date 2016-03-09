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
	<h6>*Yellow is in progress. <br>*Rounded to the nearest dollar amount</h6>
	<div>
		<span class="col col-left"><h1>Total Sales By Month</h1><canvas id = "canvasId1"></canvas></span>
		<span class="col col-right"><h1>Total Sales By Week</h1><canvas id = "canvasId2"></canvas></span>
	</div>
	<div>
		<span class="col col-left"><h1>Total Profit By Month</h1><canvas id = "canvasId3"></canvas></span>
		<span class="col col-right"><h1>Total Profit By Week</h1><canvas id = "canvasId4"></canvas></span>
	</div>
	<script src="barGraph.js"></script>
	<script type ="text/javascript" src="date.js"></script>
	<%
		ArrayList<String> monthArrayValues = new ArrayList<String>();
		ArrayList<String> weekArrayValues = new ArrayList<String>();
		ArrayList<Date> dMonthArray = new ArrayList<Date>();
		ArrayList<Date> dWeekArray = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		DateFormat monthDateFormat = new SimpleDateFormat("MM/yyyy");
		DateFormat weekDateFormat = new SimpleDateFormat("MM/dd/yyyy");
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
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		dWeekArray.add(cal.getTime());
		weekArrayValues.add(weekDateFormat.format(cal.getTime()));
		for(int i = 0; i < 4; i++)
		{
			cal.add(Calendar.WEEK_OF_YEAR, -1);
			weekArrayValues.add(weekDateFormat.format(cal.getTime()));
			dWeekArray.add(cal.getTime());
		}
		System.out.println(dMonthArray.toString());
		StringSet mSSet = transactionDB.getTotalSalesForListOfCalendarMonths(dMonthArray);
		StringSet wSSet = transactionDB.getTotalSalesForListOfCalendarWeek(dWeekArray);
		StringSet mPSet = transactionDB.getTotalProfitForListOfCalendarMonth(dMonthArray);
		StringSet wPSet = transactionDB.getTotalProfitForListOfCalendarWeek(dWeekArray);
		if(mSSet == null || wSSet == null || mPSet == null || wPSet == null)
			return;
	%>
	<script>
		var today = new Date();
		var currentYear = today.getFullYear();
		var currentMonth = today.getMonth() + 1;
		
		var graph = new BarGraph(document.getElementById("canvasId1").getContext("2d"));
		graph.margin = 2;
		graph.width = 600;
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
		              <%=mSSet.getFourValuesBack()%>,
		              <%=mSSet.getThreeValuesBack()%>,
		              <%=mSSet.getTwoValuesBack()%>,
		              <%=mSSet.getPreviousValue()%>,
		              <%=mSSet.getCurrentValue()%>
		              ]);
		
		var graph2 = new BarGraph(document.getElementById("canvasId2").getContext("2d"));
		graph2.margin = 2;
		graph2.width = 600;
		graph2.height = 400;
		var currentWeek = "<%=weekArrayValues.get(0)%>";
		var previousWeek = "<%=weekArrayValues.get(1)%>";
		var	twoWeeksBack = "<%=weekArrayValues.get(2)%>";
		var threeWeeksBack = "<%=weekArrayValues.get(3)%>";
		var fourWeeksBack = "<%=weekArrayValues.get(4)%>";
		graph2.xAxisLabelArr = [fourWeeksBack,
		                        threeWeeksBack,
		                        twoWeeksBack,
		                        previousWeek,
		                        currentWeek];
		graph2.update([
		              <%=wSSet.getFourValuesBack()%>,
		              <%=wSSet.getThreeValuesBack()%>,
		              <%=wSSet.getTwoValuesBack()%>,
		              <%=wSSet.getPreviousValue()%>,
		              <%=wSSet.getCurrentValue()%>
		              ]);
		
		var graph3 = new BarGraph(document.getElementById("canvasId3").getContext("2d"));
		graph3.margin = 2;
		graph3.width = 600;
		graph3.height = 400;
		
		var currentMonth = "<%=monthArrayValues.get(0)%>"
		var previousMonth = "<%=monthArrayValues.get(1)%>"
		var twoMonthsBack = "<%=monthArrayValues.get(2)%>"
		var threeMonthsBack = "<%=monthArrayValues.get(3)%>"
		var fourMonthsBack = "<%=monthArrayValues.get(4)%>"
		graph3.xAxisLabelArr = [fourMonthsBack, 
		                       threeMonthsBack,
		                       twoMonthsBack,
		                       previousMonth, 
		                       currentMonth];
		
		graph3.update([
		              <%=mPSet.getFourValuesBack()%>,
		              <%=mPSet.getThreeValuesBack()%>,
		              <%=mPSet.getTwoValuesBack()%>,
		              <%=mPSet.getPreviousValue()%>,
		              <%=mPSet.getCurrentValue()%>
		              ]);
		
		var graph4 = new BarGraph(document.getElementById("canvasId4").getContext("2d"));
		graph4.margin = 2;
		graph4.width = 600;
		graph4.height = 400;
		var currentWeek = "<%=weekArrayValues.get(0)%>";
		var previousWeek = "<%=weekArrayValues.get(1)%>";
		var	twoWeeksBack = "<%=weekArrayValues.get(2)%>";
		var threeWeeksBack = "<%=weekArrayValues.get(3)%>";
		var fourWeeksBack = "<%=weekArrayValues.get(4)%>";
		graph4.xAxisLabelArr = [fourWeeksBack,
		                        threeWeeksBack,
		                        twoWeeksBack,
		                        previousWeek,
		                        currentWeek];
		graph4.update([
		              <%=wPSet.getFourValuesBack()%>,
		              <%=wPSet.getThreeValuesBack()%>,
		              <%=wPSet.getTwoValuesBack()%>,
		              <%=wPSet.getPreviousValue()%>,
		              <%=wPSet.getCurrentValue()%>
		              ]);
	</script>
</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />