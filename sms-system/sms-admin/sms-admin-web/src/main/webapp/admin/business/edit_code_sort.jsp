<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="ht" uri="/hero-tags" %>
<%@ include file="/common/common.jsp" %>
<div class="pageContent">
    <form action="/admin/business_editCodeSort" cssClass="pageForm required-validate"
          onsubmit="return validateCallback(this, heroDialogAjaxDone);">
        <input hidden name="id" value="${codeSort.id}"/>
        <div class="pageFormContent" layoutH="57">
            <dl>
                <dt>分类名称:</dt>
                <dd><input maxlength="56" name="name" class="textInput" value="<c:out value="${codeSort.name}"/>" cssClass="required"
                           size="30"></input></dd>
            </dl>
            <dl>
                <dt>分类代码:</dt>
                <dd><input maxlength="56" name="code" class="textInput" value="<c:out value="${codeSort.code}"/>" cssClass="required"
                           size="30"></input></dd>
            </dl>
            <dl>
                <dt>创建时间:</dt>
                <dd>
                    <fmt:formatDate value="${codeSort.create_Date}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </dd>
            </dl>
            <div class="divider"></div>
            <dl>
                <dt>备注:</dt>
                <dd><input maxlength="2048" name="remark" class="textInput" value="<c:out value="${codeSort.remark}"/>" cols="45"
                           rows="5"></input></dd>
            </dl>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">保存</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button type="button" class="close">取消</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </form>
</div>