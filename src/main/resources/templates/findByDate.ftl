<#import "parts/common.ftl" as common>
<#import "parts/modul.ftl" as modul>
<@common.page>
<form method="post" action="/findGoods">
<input type="date" name="date" placeholder="date">
<button type="submit">Find by date</button>
</form>
</@common.page>

