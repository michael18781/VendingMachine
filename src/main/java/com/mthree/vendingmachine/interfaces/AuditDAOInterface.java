package com.mthree.vendingmachine.interfaces;

import com.mthree.vendingmachine.VendingItem;
import com.mthree.vendingmachine.exceptions.DAOException;

public interface AuditDAOInterface {
    void fileHasBeenRead(String fileRead) throws DAOException;
    void fileHasBeenWrittenTo(String fileWritten) throws DAOException;
    void itemVended(VendingItem item) throws DAOException;
    void writeAuditString(String auditMessage) throws DAOException;
}
