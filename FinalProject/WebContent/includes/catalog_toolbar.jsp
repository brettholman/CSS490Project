<section id="catalogToolbar" class="pagePart">

    <form method="post" name="form1" id="form1" action="/UserController/filter">

      Search:<input type="text" id="searchText" name="searchText"/>

      Category:<select id="category" name="category">
		  <option value="cat1">All</option>
		  <option value="cat2">Computers & Technology</option>
		  <option value="cat3">History</option>
		  <option value="cat4">Mystery, Thriller, Suspense</option>
		  <option value="cat5">Romance</option>
		  <option value="cat6">Science Fiction, Fantasy</option>
  	  </select>

      <input type="submit" id="submit" value="Go"/>

    </form>

</section>