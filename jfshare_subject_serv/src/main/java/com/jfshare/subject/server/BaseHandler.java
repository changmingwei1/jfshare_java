package com.jfshare.subject.server;


import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.subject.*;
import com.jfshare.subject.bean.*;
import com.jfshare.subject.bean.support.PageBean;
import com.jfshare.subject.model.DisplaySubjectTreeModel;
import com.jfshare.subject.model.SubjectTreeModel;
import com.jfshare.subject.server.support.IHandler;
import com.jfshare.subject.service.DisplaySubjectService;
import com.jfshare.subject.service.SubjectService;
import com.jfshare.subject.util.ResultGenerator;
import com.jfshare.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseHandler implements IHandler {

    private Logger logger = LoggerFactory.getLogger(BaseHandler.class);

    @Resource
    private SubjectService subjectService;

    @Resource
    private DisplaySubjectService displaySubjectService;

    /**
     * 将finagle对象转换成subject实体对象
     *
     * @return
     */
    public TbSubject convertSubject(SubjectInfo subjectInfo) {
        if (subjectInfo == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        TbSubject tbSubject = new TbSubject();
        tbSubject.setId(subjectInfo.getId());
        tbSubject.setName(subjectInfo.getName());
        tbSubject.setPid(subjectInfo.getPid());
        tbSubject.setSorted(subjectInfo.getSorted());
        tbSubject.setLevel(subjectInfo.getLevel());
        tbSubject.setIsLeaf(subjectInfo.getIsLeaf());
        tbSubject.setDemo(subjectInfo.getDemo());
        if (StringUtils.isNotBlank(subjectInfo.getCreateTime())) {
            tbSubject.setCreateTime(formatter.parseDateTime(subjectInfo.getCreateTime()));
        }
        tbSubject.setCreator(subjectInfo.getCreator());
        if (StringUtils.isNotBlank(subjectInfo.getUpdateTime())) {
            tbSubject.setUpdateTime(formatter.parseDateTime(subjectInfo.getUpdateTime()));
        }
        tbSubject.setUpdater(subjectInfo.getUpdater());
        tbSubject.setStatus(subjectInfo.getStatus());
        tbSubject.setPath(subjectInfo.getPath());
        return tbSubject;
    }

    /**
     * 将subject实体对象转换成finagle对象
     *
     * @return
     */
    public SubjectInfo rConvertSubject(TbSubject tbSubject) {
        if (tbSubject == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        SubjectInfo subjectInfo = new SubjectInfo();
        subjectInfo.setId(tbSubject.getId());
        subjectInfo.setName(tbSubject.getName());
        subjectInfo.setPid(tbSubject.getPid());
        subjectInfo.setSorted(tbSubject.getSorted());
        subjectInfo.setLevel(tbSubject.getLevel());
        subjectInfo.setIsLeaf(tbSubject.getIsLeaf());
        subjectInfo.setDemo(tbSubject.getDemo());
        subjectInfo.setCreateTime(tbSubject.getCreateTime().toString(formatter));
        subjectInfo.setCreator(tbSubject.getCreator());
        subjectInfo.setUpdateTime(tbSubject.getUpdateTime().toString(formatter));
        subjectInfo.setUpdater(tbSubject.getUpdater());
        subjectInfo.setStatus(tbSubject.getStatus());
        subjectInfo.setPath(tbSubject.getPath());
        subjectInfo.setDisplayIds(tbSubject.getDisplayIds());
        subjectInfo.setSubjectNodes(rConvertSubjectTreeModels(this.subjectService.getSuperTreeForManage(tbSubject.getId())));
        return subjectInfo;
    }


    /**
     * 将subject实体对象转换成finagle对象
     *
     * @return
     */
    public List<SubjectInfo> rConvertSubjects(List<TbSubject> tbSubjects) {
        if (tbSubjects == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        List<SubjectInfo> subjectInfos = new ArrayList<>();
        for (TbSubject tbSubject : tbSubjects) {
            SubjectInfo subjectInfo = new SubjectInfo();
            subjectInfo.setId(tbSubject.getId());
            subjectInfo.setName(tbSubject.getName());
            subjectInfo.setPid(tbSubject.getPid());
            subjectInfo.setSorted(tbSubject.getSorted());
            subjectInfo.setLevel(tbSubject.getLevel());
            subjectInfo.setIsLeaf(tbSubject.getIsLeaf());
            subjectInfo.setDemo(tbSubject.getDemo());
            subjectInfo.setCreateTime(tbSubject.getCreateTime().toString(formatter));
            subjectInfo.setCreator(tbSubject.getCreator());
            subjectInfo.setUpdateTime(tbSubject.getUpdateTime().toString(formatter));
            subjectInfo.setUpdater(tbSubject.getUpdater());
            subjectInfo.setStatus(tbSubject.getStatus());
            subjectInfo.setPath(tbSubject.getPath());
            subjectInfo.setDisplayIds(tbSubject.getDisplayIds());
            subjectInfo.setSubjectNodes(rConvertSubjectTreeModels(this.subjectService.getSuperTreeForManage(tbSubject.getId())));
        }
        return subjectInfos;
    }

    /**
     * 将subject node实体对象转换成finagle对象
     *
     * @return
     */
    public SubjectNode rConvertSubjectTreeModel(SubjectTreeModel model) {
        if (model == null) {
            return null;
        }
        SubjectNode subjectNode = new SubjectNode();
        subjectNode.setId(model.getId());
        subjectNode.setName(model.getName());
        subjectNode.setPid(model.getPid());
        subjectNode.setSorted(model.getSorted());
        subjectNode.setIsLeaf(model.getIsLeaf());
        subjectNode.setDisplayIds(model.getDisplayIds());
        subjectNode.setStatus(model.getStatus());
        return subjectNode;
    }

    /**
     * 批量将subject node实体对象转换成finagle对象
     *
     * @return
     */
    public List<SubjectNode> rConvertSubjectTreeModels(List<SubjectTreeModel> models) {
        List<SubjectNode> nodes = new ArrayList<>();
        for (SubjectTreeModel model : models) {
            nodes.add(rConvertSubjectTreeModel(model));
        }
        return nodes;
    }


    /**
     * 将finagle对象转换成TbDisplaySubject实体对象
     *
     * @param displaySubjectInfo
     * @return
     */
    public TbDisplaySubject convertDisSubject(DisplaySubjectInfo displaySubjectInfo) {
        if (displaySubjectInfo == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        TbDisplaySubject tbDisplaySubject = new TbDisplaySubject();
        tbDisplaySubject.setId(displaySubjectInfo.getId());
        tbDisplaySubject.setName(displaySubjectInfo.getName());
        tbDisplaySubject.setPid(displaySubjectInfo.getPid());
        tbDisplaySubject.setImage(displaySubjectInfo.getImg_key());
        tbDisplaySubject.setSorted(displaySubjectInfo.getSorted());
        tbDisplaySubject.setLevel(displaySubjectInfo.getLevel());
        tbDisplaySubject.setIsLeaf(displaySubjectInfo.getIsLeaf());
        tbDisplaySubject.setDemo(displaySubjectInfo.getDemo());
        if (StringUtils.isNotBlank(displaySubjectInfo.getCreateTime())) {
            tbDisplaySubject.setCreateTime(formatter.parseDateTime(displaySubjectInfo.getCreateTime()));
        }
        tbDisplaySubject.setCreator(displaySubjectInfo.getCreator());
        if (StringUtils.isNotBlank(displaySubjectInfo.getUpdateTime())) {
            tbDisplaySubject.setUpdateTime(formatter.parseDateTime(displaySubjectInfo.getUpdateTime()));
        }
        tbDisplaySubject.setUpdater(displaySubjectInfo.getUpdater());
        tbDisplaySubject.setDeleted(displaySubjectInfo.getDeleted());

        return tbDisplaySubject;
    }

    /**
     * 将TbDisplaySubject实体对象转换成finagle对象
     *
     * @param tbDisplaySubject
     * @return
     */
    public DisplaySubjectInfo rConvertDisSubject(TbDisplaySubject tbDisplaySubject) {
        if (tbDisplaySubject == null) {
            return null;
        }
        DisplaySubjectInfo displaySubjectInfo = new DisplaySubjectInfo();
        displaySubjectInfo.setId(tbDisplaySubject.getId());
        displaySubjectInfo.setName(tbDisplaySubject.getName());
        displaySubjectInfo.setPid(tbDisplaySubject.getPid());
        displaySubjectInfo.setImg_key(tbDisplaySubject.getImage());
        displaySubjectInfo.setSorted(tbDisplaySubject.getSorted());
        displaySubjectInfo.setLevel(tbDisplaySubject.getLevel());
        displaySubjectInfo.setIsLeaf(tbDisplaySubject.getIsLeaf());
        displaySubjectInfo.setDemo(tbDisplaySubject.getDemo());
        if (tbDisplaySubject.getCreateTime() != null) {
            displaySubjectInfo.setCreateTime(DateUtils.toDateTimeStr(tbDisplaySubject.getCreateTime().toDate()));
        }
        displaySubjectInfo.setCreator(tbDisplaySubject.getCreator());
        if (tbDisplaySubject.getUpdateTime() != null) {
            displaySubjectInfo.setUpdateTime(DateUtils.toDateTimeStr(tbDisplaySubject.getUpdateTime().toDate()));
        }
        displaySubjectInfo.setUpdater(tbDisplaySubject.getUpdater());
        displaySubjectInfo.setDeleted(tbDisplaySubject.getDeleted());
        return displaySubjectInfo;
    }

    public TbProductAttribute convertAttribute(SubjectAttribute attribute) {
        if (attribute == null) {
            return null;
        }
        TbProductAttribute tAttribute = new TbProductAttribute();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isNotBlank(attribute.getCreateTime())) {
            tAttribute.setCreateTime(formatter.parseDateTime(attribute.getCreateTime()));
        }
        tAttribute.setCreator(attribute.getCreator());
        tAttribute.setDeleted(attribute.getDeleted());
        tAttribute.setDemo(attribute.getDemo());
        tAttribute.setId(attribute.getId());
        tAttribute.setIsSku(attribute.getIsSku());
        tAttribute.setName(attribute.getName());
        tAttribute.setRequired(attribute.getBeRequired());
        tAttribute.setRewriteId(attribute.getRewriteId());
        tAttribute.setSubjectId(attribute.getSubjectId());
        tAttribute.setType(attribute.getType());
        tAttribute.setUpdater(attribute.getUpdater());
        if (StringUtils.isNotBlank(attribute.getUpdateTime())) {
            tAttribute.setUpdateTime(formatter.parseDateTime(attribute.getUpdateTime()));
        }
        tAttribute.setValue(attribute.getValue());
        return tAttribute;
    }

    public SubjectAttribute rConvertAttribute(TbProductAttribute attribute) {
        if (attribute == null) {
            return null;
        }
        SubjectAttribute tAttribute = new SubjectAttribute();
        if (attribute.getCreateTime() != null) {
            tAttribute.setCreateTime(DateUtils.toDateTimeStr(attribute.getCreateTime().toDate()));
        }
        tAttribute.setCreator(attribute.getCreator());
        tAttribute.setDeleted(attribute.getDeleted());
        tAttribute.setDemo(attribute.getDemo());
        tAttribute.setId(attribute.getId());
        tAttribute.setIsSku(attribute.getIsSku());
        tAttribute.setName(attribute.getName());
        tAttribute.setBeRequired(attribute.getRequired());
        tAttribute.setRewriteId(attribute.getRewriteId());
        tAttribute.setSubjectId(attribute.getSubjectId());
        tAttribute.setType(attribute.getType());
        tAttribute.setUpdater(attribute.getUpdater());
        if (attribute.getUpdateTime() != null) {
            tAttribute.setUpdateTime(DateUtils.toDateTimeStr(attribute.getUpdateTime().toDate()));
        }
        tAttribute.setValue(attribute.getValue());
        return tAttribute;
    }

    public List<SubjectAttribute> rConvertAttributes(List<TbProductAttribute> attributes) {
        List<SubjectAttribute> subjectAttributes = new ArrayList<>();
        if (attributes != null) {
            for (TbProductAttribute attribute : attributes) {
                subjectAttributes.add(rConvertAttribute(attribute));
            }
        }

        return subjectAttributes;
    }

    public List<DisplaySubjectNode> rConvertDisSubjectNode(List<DisplaySubjectTreeModel> nodes) {
        List<DisplaySubjectNode> renodes = new ArrayList<DisplaySubjectNode>();
        if (nodes == null) {
            return renodes;
        }
        for (DisplaySubjectTreeModel node : nodes) {
            DisplaySubjectNode tnode = new DisplaySubjectNode();
            tnode.id = node.getId();
            tnode.img_key = node.getImage();
            tnode.isLeaf = node.getIsLeaf();
            tnode.name = node.getName();
            tnode.pid = node.getPid();
            tnode.sorted = node.getSorted();
            renodes.add(tnode);
        }
        return renodes;
    }

    /**
     * 将finagle对象转换成TbDisplaySubjectChannel实体对象
     *
     * @param channelInfo
     * @return
     */
    public TbDisplaySubjectChannel convertDisplaySubjectChannel(DisplaySubjectChannelInfo channelInfo) {
        if (channelInfo == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        TbDisplaySubjectChannel channel = new TbDisplaySubjectChannel();
        channel.setId(channelInfo.getId());
        channel.setName(channelInfo.getName());
        channel.setCode(channelInfo.getCode());
        channel.setCreator(channelInfo.getCreator());
        channel.setUpdater(channelInfo.getUpdater());
        if(channelInfo.getUpdateTime() != null) {
            channel.setUpdateTime(formatter.parseDateTime(channelInfo.getUpdateTime()));
        }
        channel.setUpdaterName(channelInfo.getUpdaterName());
        return channel;
    }

    /**
     * 将TbDisplaySubjectChannel实体对象转换成finagle对象
     *
     * @param channel
     * @return
     */
    public DisplaySubjectChannelInfo rConvertDisplaySubjectChannel(TbDisplaySubjectChannel channel) {
        if (channel == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DisplaySubjectChannelInfo channelInfo = new DisplaySubjectChannelInfo();
        channelInfo.setId(channel.getId());
        channelInfo.setName(channel.getName());
        channelInfo.setCode(channel.getCode());
        channelInfo.setCreator(channel.getCreator());
        channelInfo.setUpdater(channel.getUpdater());
        channelInfo.setUpdateTime(DateUtils.toDateTimeStr(channel.getUpdateTime().toDate()));
        channelInfo.setUpdaterName(channel.getUpdaterName());
        return channelInfo;
    }

    /**
     * 将finagle对象转换成TbSubjectRefDisplay实体对象
     *
     * @param info
     * @return
     */
    public TbSubjectRefDisplay convertSubjectRefDisplay(SubjectRefDisplayInfo info) {
        if (info == null) {
            return null;
        }
        TbSubjectRefDisplay tbSubjectRefDisplay = new TbSubjectRefDisplay();
        tbSubjectRefDisplay.setSubjectId(info.getSubjectId());
        tbSubjectRefDisplay.setDisplayId(info.getDisplayId());
        return tbSubjectRefDisplay;
    }

    /**
     * 将finagle对象转换成PageBean实体对象
     *
     * @param page
     * @return
     */
    public PageBean convertPage(Page page) {
        if (page == null) {
            return null;
        }
        PageBean pageBean = new PageBean(page.getPageSize(), page.getCurPage());
        pageBean.setCurPage(page.getCurPage());
        pageBean.setTotal(page.getTotal());
        return pageBean;
    }

    /**
     * 将PageBean实体对象转换成finagle对象
     *
     * @param pageBean
     * @return
     */
    public Page rConvertPage(PageBean pageBean) {
        if (pageBean == null) {
            return null;
        }
        Page page = new Page();
        page.setPageSize(pageBean.getPageSize());
        page.setPageCount(pageBean.getPageCount());
        page.setCurPage(pageBean.getCurPage());
        page.setTotal(pageBean.getTotal());
        return page;
    }

    /**
     * 验证后台类目信息
     *
     * @param subjectInfo
     * @return
     */
    protected Result verifySubject(SubjectInfo subjectInfo) {
        Result result = new Result(0);
        //验证名称是否为空
        if (StringUtils.isBlank(subjectInfo.getName())) {
            result.setCode(1);
            result.addToFailDescList(ResultGenerator.SUJECT_NAME_NOT_NULL);
        }
        //如果父ID不为0，需要验证父节点是已发布或者删除状态的叶子节点，不能添加子节点
        if (subjectInfo.getPid() != 0) {
            TbSubject parent = this.subjectService.getById(subjectInfo.getPid());
            //parent 为 null，状态为 新增（1）
            if (parent != null && (parent.getStatus() == 2 || parent.getStatus() == 3) && parent.getIsLeaf() == 1) {
                result.setCode(1);
                result.addToFailDescList(ResultGenerator.SUJECT_PARENT_NOT_LEAF);
            }
        }
        return result;
    }

    /**
     * 验证后台类目信息
     *
     * @param displaySubjectInfo
     * @return
     */
    protected Result verifyDisplaySubject(DisplaySubjectInfo displaySubjectInfo) {
        Result result = new Result(0);
        //验证名称是否为空
        if (StringUtils.isBlank(displaySubjectInfo.getName())) {
            result.setCode(1);
            result.addToFailDescList(ResultGenerator.SUJECT_NAME_NOT_NULL);
        }
        //如果父ID不为0，需要验证父节点是已发布状态的叶子节点，不能添加子节点
        if (displaySubjectInfo.getPid() != 0) {
            TbDisplaySubject parent = this.displaySubjectService.getById(displaySubjectInfo.getPid());
            if (StringUtils.isNotBlank(parent.getSubjectIds())) {
                result.setCode(1);
                result.addToFailDescList(ResultGenerator.SUJECT_PARENT_NOT_LEAF);
            }
        }

        //sort 请输入大于等于0的整数，按数字从小到大排序
        if(displaySubjectInfo.getSorted() < 0) {
            result.setCode(1);
            result.addToFailDescList(ResultGenerator.SUJECT_SORT_NUM);
        }
        return result;
    }
}
