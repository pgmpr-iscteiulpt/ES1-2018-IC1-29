package bda;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.swing.table.AbstractTableModel;

/**
 * Implementa um modelo de JTable adaptado � listagem de emails e posts
 * @author Grupo 29
 * @version 1.0
 */

@SuppressWarnings("serial")
public class BDATableModel extends AbstractTableModel {
	
	private Message[] messages;
	private String[] columnNames = new String[] { "Data e Hora", "Fonte", "Remetente", "Assunto" };

	/**
	 * Construtor que recebe uma lista de mensagens para apresentar
	 * @param msgs Mensagem a apresentar
	 */
	public BDATableModel(Message[] msgs) {
		messages = msgs;
	}

	/**
	 * M�todo que devolve o n�mero de colunas
	 * @return Integer (n�mero de colunas)
	 */
	@Override
	public int getColumnCount() {
		return 4;
	}

	/**
	 * M�todo que devolve o n�mero de linhas
	 * @return Integer (n�mero de linhas)
	 */
	@Override
	public int getRowCount() {
		return messages.length;
	}

	/**
	 * M�todo que devolve o valor de uma determinada c�lula
	 * @return Object (informa��o da mensagem contida na c�lula)
	 */
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

	/**
	 * M�todo que devolve uma String com o nome da coluna
	 * @return String (nome da coluna)
	 */
	public String getColumnName(int column) {
		return columnNames[column];
	}

}
