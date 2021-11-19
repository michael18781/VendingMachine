package com.mthree.vendingmachine;

import com.mthree.vendingmachine.exceptions.DAOException;
import com.mthree.vendingmachine.interfaces.AuditDAOInterface;

import java.io.*;
import java.time.LocalDateTime;

public class AuditDAO implements AuditDAOInterface {
    String auditFile;

    public AuditDAO(String auditFile){
        this.auditFile = auditFile;
    }

    public void fileHasBeenRead(String fileRead) throws DAOException {
        writeAuditString("FILE " + fileRead + " HAS BEEN READ AT " + LocalDateTime.now());
    }

    public void fileHasBeenWrittenTo(String fileWritten) throws DAOException {
        writeAuditString("FILE " + fileWritten + " HAS BEEN WRITTEN TO AT " + LocalDateTime.now());

    }

    public void itemVended(VendingItem item) throws DAOException {
        writeAuditString("ITEM (NAME: " + item.getItemName() + " COST: " + item.getItemCost() + " NUMBER_REMAINING: " + item.getNumberItems() + ")" + " HAS BEEN VENDED AT " + LocalDateTime.now());
    }

    public void writeAuditString(String auditMessage) throws DAOException {
        try {
            FileWriter fw = new FileWriter(auditFile, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(auditMessage + "\n");

            bw.close();
            fw.close();
        } catch (IOException e){
            throw new DAOException("There has been an error writing (AUDIT) to the file.");
        }
    }
}
