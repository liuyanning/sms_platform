<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="ht" uri="/hero-tags" %>
<%@ include file="/common/common.jsp" %>
<div class="pageContent">
    <form action="/admin/business_editMobileArea" cssClass="pageForm required-validate"
          onsubmit="return validateCallback(this, heroDialogAjaxDone);">
        <input hidden name="id" value="${mobileArea.id}"/>
        <div class="pageFormContent" layoutH="57">
            <dl>
                <dt>号码前7位:</dt>
                <dd><input maxlength="50" name="mobileNumber" class="textInput" value="<c:out value="${mobileArea.mobileNumber}"/>"
                           cssClass="required digits" minlength="7" maxlength="7" size="30"></input></dd>
            </dl>
            <dl>
                <dt>号码归属地:</dt>
                <dd><input maxlength="50" name="mobileArea" class="textInput" value="<c:out value="${mobileArea.mobileArea}"/>"
                           cssClass="required" size="30"></input></dd>
            </dl>
            <dl>
                <dt>号码类型:</dt>
                <dd><input maxlength="50" name="mobileType" class="textInput" value="<c:out value="${mobileArea.mobileType}"/>"
                           size="30"/></dd>
            </dl>
            <dl>
                <dt>区号:</dt>
                <dd><input maxlength="50" name="areaCode" class="textInput" value="<c:out value="${mobileArea.areaCode}"/>"
                           cssClass="digits" size="30"></input></dd>
            </dl>
            <dl>
                <dt>邮编:</dt>
                <dd><input maxlength="50" name="postCode" class="textInput" value="<c:out value="${mobileArea.postCode}"/>"
                           cssClass="digits" size="30"></input></dd>
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