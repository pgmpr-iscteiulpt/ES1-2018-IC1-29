package bda;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;

import javax.mail.MessagingException;

/**
 * Cria um enumerado que representa os meses em formato de data
 * 
 * @author Grupo 29
 * @version 2.0
 */
enum Months {
	Jan(1), Feb(2), Mar(3), Apr(4), May(5), Jun(6), Jul(7), Aug(8), Sep(9), Oct(10), Nov(11), Dec(12);

	private final int a;

	Months(int a) {
		this.a = a;
	}

	public int getA() {
		return a;
	}

}

/**
 * Cria uma classe que compara dois conteúdos
 * 
 * @author Grupo 29
 * @version 2.0
 */
public class DateComparator implements Comparator<Content> {

	@SuppressWarnings("null")
	@Override
	public int compare(Content arg0, Content arg1) {
		String a = null;
		String b = null;
		try {
			a = arg0.getHashCode();
			b = arg1.getHashCode();
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}

		if (a.length() == 18 && b.length() == 18) {
			int year = Integer.parseInt(a.substring(6, 8));
			Months month = Months.valueOf(a.substring(3, 6));
			int day = Integer.parseInt(a.substring(8, 12));
			int hour = Integer.parseInt(a.substring(12));

			int year1 = Integer.parseInt(b.substring(6, 8));
			Months month1 = Months.valueOf(b.substring(3, 6));
			int day1 = Integer.parseInt(b.substring(8, 12));
			int hour1 = Integer.parseInt(b.substring(12));

			if (year != year1) {
				return year - year1;
			} else {
				if (month != month1) {
					return month.getA() - month1.getA();
				} else {
					if (day != day1) {
						return day - day1;
					}
					if (hour != hour1) {
						return hour - hour1;
					} else {
						return 0;
					}
				}

			}
		}
		return (Integer) null;
	}

	public boolean moreThanDay(String date) {
		long dailyMillies = 24 * 60 * 60 * 1000L;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] simpleDate = date.split(" ");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = sdf.parse(simpleDate[5] + "-" + Months.valueOf(simpleDate[1]).getA() + "-" + simpleDate[2]);
			date2 = sdf.parse(LocalDate.now().toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		boolean moreThanDay = Math.abs(date1.getTime() - date2.getTime()) > dailyMillies;

		return moreThanDay;

	}

}
