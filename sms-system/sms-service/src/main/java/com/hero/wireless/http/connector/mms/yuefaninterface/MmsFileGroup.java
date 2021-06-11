
package com.hero.wireless.http.connector.mms.yuefaninterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>mmsFileGroup complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="mmsFileGroup">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="audio_FileData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="audio_FileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="image_FileData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="image_FileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="playTime" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="text_FileData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="text_FileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="txtPosition" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mmsFileGroup", propOrder = {
    "audioFileData",
    "audioFileName",
    "imageFileData",
    "imageFileName",
    "playTime",
    "textFileData",
    "textFileName",
    "txtPosition"
})
public class MmsFileGroup {

    @XmlElement(name = "audio_FileData")
    protected String audioFileData;
    @XmlElement(name = "audio_FileName")
    protected String audioFileName;
    @XmlElement(name = "image_FileData")
    protected String imageFileData;
    @XmlElement(name = "image_FileName")
    protected String imageFileName;
    protected Integer playTime;
    @XmlElement(name = "text_FileData")
    protected String textFileData;
    @XmlElement(name = "text_FileName")
    protected String textFileName;
    protected Integer txtPosition;

    /**
     * ��ȡaudioFileData���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAudioFileData() {
        return audioFileData;
    }

    /**
     * ����audioFileData���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAudioFileData(String value) {
        this.audioFileData = value;
    }

    /**
     * ��ȡaudioFileName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAudioFileName() {
        return audioFileName;
    }

    /**
     * ����audioFileName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAudioFileName(String value) {
        this.audioFileName = value;
    }

    /**
     * ��ȡimageFileData���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageFileData() {
        return imageFileData;
    }

    /**
     * ����imageFileData���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageFileData(String value) {
        this.imageFileData = value;
    }

    /**
     * ��ȡimageFileName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageFileName() {
        return imageFileName;
    }

    /**
     * ����imageFileName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageFileName(String value) {
        this.imageFileName = value;
    }

    /**
     * ��ȡplayTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPlayTime() {
        return playTime;
    }

    /**
     * ����playTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPlayTime(Integer value) {
        this.playTime = value;
    }

    /**
     * ��ȡtextFileData���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextFileData() {
        return textFileData;
    }

    /**
     * ����textFileData���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextFileData(String value) {
        this.textFileData = value;
    }

    /**
     * ��ȡtextFileName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextFileName() {
        return textFileName;
    }

    /**
     * ����textFileName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextFileName(String value) {
        this.textFileName = value;
    }

    /**
     * ��ȡtxtPosition���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTxtPosition() {
        return txtPosition;
    }

    /**
     * ����txtPosition���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTxtPosition(Integer value) {
        this.txtPosition = value;
    }

}
