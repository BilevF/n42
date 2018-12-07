package com.bilev.tools;

import com.bilev.dto.BasicContractDto;
import com.bilev.dto.ContractDto;
import com.bilev.model.Block;
import com.bilev.model.Contract;
import org.springframework.stereotype.Service;

@Service
public class ContractCreator implements Creator<ContractDto, BasicContractDto, Contract> {

    private final String phoneNumber = "911";

    private final Double balance = 0.0;

    private final Integer userId = 0;

    @Override
    public ContractDto getDto(int id) {
        ContractDto contract = new ContractDto();

        contract.setId(id);
        contract.setPhoneNumber(id + phoneNumber);
        contract.setBalance(balance);
        contract.setUserId(userId);

        return contract;
    }

    @Override
    public BasicContractDto getBasicDto(int id) {
        BasicContractDto contract = new BasicContractDto();

        contract.setId(id);
        contract.setPhoneNumber(id + phoneNumber);
        contract.setBalance(balance);
        contract.setUserId(userId);

        return contract;
    }

    @Override
    public Contract getEntity(int id) {
        Contract contract = new Contract();

        contract.setId(id);
        contract.setPhoneNumber(id + phoneNumber);
        contract.setBalance(balance);

        return contract;
    }

    public Block getBlock(Block.BlockType type) {
        Block res = new Block();
        res.setBlockType(type);
        res.setId(0);

        return res;
    }

}
