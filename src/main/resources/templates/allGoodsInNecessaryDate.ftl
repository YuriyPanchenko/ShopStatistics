<#import "parts/common.ftl" as common>
<#import "parts/modul.ftl" as module>
<@common.page>


<@module.showGoods dateList>


</@module.showGoods>


<#if dateList??>

<#else>
    <form action="/deleteByDate/${date}" method="POST">
        <input type="submit" name="Delete" value="Delete" />
    </form>
</#if>

</@common.page>
