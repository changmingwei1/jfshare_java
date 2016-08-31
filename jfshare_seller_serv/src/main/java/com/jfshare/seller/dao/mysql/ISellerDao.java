package com.jfshare.seller.dao.mysql;

import com.jfshare.finagle.thrift.seller.Seller;
import com.jfshare.seller.model.TbSeller;
import com.jfshare.seller.model.TbUserSellerRela;

import java.util.List;

/**
 * Created by lenovo on 2015/9/28.
 */
public interface ISellerDao {
    int sellerIsExist(String loginName);

    int insert(TbSeller seller);

    int update(TbSeller seller);

    TbSeller getSeller(int sellerId);

    List<TbSeller> checkLogin(Seller seller);

    List<TbSeller> getSellerList(List<Integer> sellerIds);
    
    List<TbSeller> getSellerBySeller(Seller seller);
    //增加会员信息
    int insertUserSellerRela(TbUserSellerRela tbUserSellerRela);
    //删除会员信息
    int deleteByuserId(String userId);
    //查询会员信息列表
    List<TbUserSellerRela>  selectUsrListBySellerId(String sellerId);
    
}
