<#if (actionMessages?exists && actionMessages?size > 0)>
  <#list actionMessages as message>
   ${message}
  </#list>
</#if>