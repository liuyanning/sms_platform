<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="ht" uri="/hero-tags" %>
<%@ include file="/common/common.jsp" %>
<div class="pageContent">
    <form action="/admin/business_editCode" cssClass="pageForm required-validate"
          onsubmit="return validateCallback(this, heroDialogAjaxDone);">
        <input hidden name="id" value="${code.id}"/>
        <div class="pageFormContent" layoutH="57">
            <dl>
                <dt>名称:</dt>
                <dd><input maxlength="2048" name="name" class="textInput" value="<c:out value="${code.name}"/>" cssClass="required"
                           size="30"></input></dd>
            </dl>
            <dl>
                <dt>代码:</dt>
                <dd><input maxlength="2048" name="code" class="textInput" value="<c:out value="${code.code}"/>" cssClass="required"
                           size="30"></input></dd>
            </dl>
            <dl>
                <dt>上级代码:</dt>
                <dd><input maxlength="56" name="up_Code" class="textInput" value="<c:out value="${code.up_Code}"/>" size="30"></input>
                </dd>
            </dl>
            <dl>
                <dt>分类代码:</dt>
                <dd><ht:herocodesortselect cssClass="required" name="sort_Code" selected="${code.sort_Code}"/></dd>
            </dl>
            <dl>
                <dt>创建时间:</dt>
                <dd>
                    <fmt:formatDate value="${code.create_Date}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </dd>
            </dl>
            <div class="divider"></div>
            <dl>
                <dt>备注:</dt>
                <dd><input maxlength="2048" name="remark" class="textInput" value="<c:out value="${code.remark}"/>" cols="45"
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