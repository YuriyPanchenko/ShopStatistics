<#import "parts/common.ftl" as common>
<#import "parts/modul.ftl" as module>
<@common.page>

    <@module.totalProfit>

    </@module.totalProfit>

<div>
    <div class="container">
        <#if total??>
            Toral profit:
            ${total}
            ${currency}
        <#else>
            This will be your profit
        </#if>
    </div>
</div>

</@common.page>