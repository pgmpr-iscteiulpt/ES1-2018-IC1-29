package bda;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class BDATableModel extends AbstractTableModel {
	
	private Message[] messages;
	private String[] columnNames = new String[] { "Data e Hora", "Fonte", "Remetente", "Assunto" };

	public BDATableModel(Message[] msgs) {
		messages = msgs;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return messages.length;
	}

	@Override
	public Object getValueAt(int row, int column) {
		Message msg = messages[row];
		try {
			switch (column) {
			case 0:
				return msg.getReceivedDate();
			case 1:
				Address[] a = msg.getFrom();
				return ((InternetAddress) a[0]).getAddress();
			case 2:
				return "Gmail";
			case 3:
				return msg.getSubject();
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

}
