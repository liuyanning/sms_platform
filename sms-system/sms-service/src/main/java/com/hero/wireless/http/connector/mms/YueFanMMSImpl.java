package com.hero.wireless.http.connector.mms;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ReportStatus;
import com.hero.wireless.enums.SubmitStatus;
import com.hero.wireless.http.AbstractHttp;
import com.hero.wireless.http.connector.mms.yuefaninterface.MmsOperatorImpService;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.Submit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.ws.Holder;

/**
 * Title: YueFanMMSImpl
 * Description:悦凡营销彩信，只支持图片 文字 音频  不支持视频短信
 * @author ywj
 * @date 2020-04-13
 */
public class YueFanMMSImpl extends AbstractHttp {

    /**
     * report(Channel channel)
     *   getMmsReport接口：参数说明
     *     arg0: account
     *     arg1: password
     *     arg2: responseStr 输出参数，下发彩信的状态报告
     *     arg3: errMsg 输出参数，小于零是表示错误，详情见错误码
     * */
    @Override
    public void report(Channel channel) throws Exception {
//        String url = reportUrl(channel);
//        String url = "http://101.132.222.2:8899/sms/webService/mmsOper?wsdl";
        Holder<String> responseStr = new Holder<>("");
        Holder<String> errMsg = new Holder<>("");
        MmsOperatorImpService moiService = new MmsOperatorImpService();
        moiService.getMmsOperatorImpPort().getMmsReport(channel.getAccount(),channel.getPassword(),responseStr,errMsg);
        SuperLogger.debug("responseStr:"+responseStr.value+",errMsg:"+errMsg.value);
        if(StringUtils.isEmpty(responseStr.value) || StringUtils.isEmpty(errMsg.value) ||!"0".equals(errMsg.value))
            return ;
        Document resultDocument = DocumentHelper.parseText(responseStr.value);
        Element rootElement = resultDocument.getRootElement();
        List<?> reportElementList = rootElement.elements("mms");
        if (ObjectUtils.isEmpty(reportElementList))
            return ;
        reportElementList.forEach(item -> {
            Element report = (Element) item;
            Report entity = new Report();
            entity.setChannel_No(channel.getNo());
            entity.setChannel_Msg_Id(report.elementText("mmsID"));
            entity.setPhone_No(report.elementText("phone"));
            String stat = report.elementText("stat");
            entity.setNative_Status(stat);
            entity.setStatus_Code("0".equals(stat.trim())?ReportStatus.SUCCESS.toString():ReportStatus.FAILD.toString());
            saveReport(entity);
        });
    }

    /**
     * submit(Submit submit)
     *  sendMms接口：提交上游，参数说明
     *    arg0: account
     *    arg1: password
     *    arg2: productID 彩信产品id
     *    arg3: mmsType 彩信业务类型 todo 等悦凡技术联调的时候确认此参数传值
     *    arg4: sendTime 下发时间
     *    arg5: inPhones 下发号码，多个号码用半角逗号“,”分割
     *    arg6: resMsg 输出参数，提交彩信成功的场合，服务端返回
     *    arg7: errMsg 输出参数，小于零是表示错误，详情见错误码
     * */
    @Override
    public SubmitStatus submit(Submit submit) throws Exception {
//        String postURL = "http://101.132.222.2:8899/sms/webService/mmsOper?wsdl";
//        String postURL = submitUrl(channel);
        Channel channel = DatabaseCache.getChannelByNo(submit.getChannel_No());
        Holder<String> resMsg = new Holder<>("");
        Holder<String> errMsg = new Holder<>("");
        new MmsOperatorImpService().getMmsOperatorImpPort().sendMms(channel.getAccount(),channel.getPassword()
                ,submit.getContent(),"",getXMLGregorianCalendar(new Date()),submit.getPhone_No(),resMsg,errMsg);
        SuperLogger.debug("resMsg:"+resMsg.value+",errMsg:"+errMsg.value);
        submit.setSubmit_Description(errMsg.value);
        if(StringUtils.isEmpty(resMsg.value) || StringUtils.isEmpty(errMsg.value) ||!"0".equals(errMsg.value)){
            SuperLogger.error("悦凡彩信提交失败,失败码:"+ errMsg.value);
            return SubmitStatus.FAILD;
        }
        Document resultDocument = DocumentHelper.parseText(resMsg.value);
        Element mms = resultDocument.getRootElement().element("mms");
        submit.setChannel_Msg_Id(mms.elementTextTrim("mmsID"));
        return SubmitStatus.SUCCESS;
    }

    public javax.xml.datatype.XMLGregorianCalendar getXMLGregorianCalendar(Date date) throws Exception
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        javax.xml.datatype.DatatypeFactory dtf = javax.xml.datatype.DatatypeFactory.newInstance();
        return dtf.newXMLGregorianCalendar(
                calendar.get(calendar.YEAR),
                calendar.get(calendar.MONTH)+1,
                calendar.get(calendar.DAY_OF_MONTH),
                calendar.get(calendar.HOUR),
                calendar.get(calendar.MINUTE),
                calendar.get(calendar.SECOND),
                calendar.get(calendar.MILLISECOND),
                calendar.get(calendar.ZONE_OFFSET)/(1000*60));
    }

    @Override
    public String balance(Channel channel) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void mo(Channel channel) throws Exception {
        // TODO Auto-generated method stub
    }

    public static void main(String[] args){
        try {
            Submit submit = new Submit();
            submit.setContent("1223331");//彩信产品id
            submit.setPhone_No("15566666666");
            YueFanMMSImpl y = new YueFanMMSImpl();
            SubmitStatus submitStatus = y.submit(submit);
            System.out.println(submitStatus.toString());
        }catch (Exception e ){
            e.printStackTrace();
        }
    }
}
