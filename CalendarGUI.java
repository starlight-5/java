import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
public class CalendarGUI extends JFrame {
    private JPanel calendarPanel;
    private JLabel monthLabel;
    private LocalDate currentDate;
    private ScheduleManager smg;
    private List<ScheduleEvent> []list;
    public CalendarGUI() {
        setTitle("Swing Calendar");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        currentDate = LocalDate.now();

        // 상단 패널 (월 변경)
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");
        monthLabel = new JLabel("", SwingConstants.CENTER);

        smg = new PmsSchaduleManager();
        prevButton.addActionListener(e -> {
            currentDate = currentDate.minusMonths(1);
            updateCalendar();
        });

        nextButton.addActionListener(e -> {
        	
            currentDate = currentDate.plusMonths(1);
            // 그달에 이벤트 리스트를 이벤트 리스트를 담는 리스트에 저장
            updateCalendar();
        });

        topPanel.add(prevButton, BorderLayout.WEST);
        topPanel.add(monthLabel, BorderLayout.CENTER);
        topPanel.add(nextButton, BorderLayout.EAST);

        // 달력 패널
        calendarPanel = new JPanel(new GridLayout(0, 7, 3, 3));

        add(topPanel, BorderLayout.NORTH);
        add(calendarPanel, BorderLayout.CENTER);

        updateCalendar();
    }
//페이지 넘어가는 업데이트 기존거는 유지
    private void updateCalendar() {
        calendarPanel.removeAll();

        // 월 레이블 업데이트
        YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonth());
        monthLabel.setText(currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + currentDate.getYear());

        // 요일 출력
        for (DayOfWeek dow : DayOfWeek.values()) {
            calendarPanel.add(new JLabel(dow.getDisplayName(TextStyle.SHORT, Locale.ENGLISH), SwingConstants.CENTER));
        }

        // 시작 요일 및 총 일 수
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int startDay = firstDayOfMonth.getDayOfWeek().getValue(); // 1 (Mon) ~ 7 (Sun)
        int daysInMonth = yearMonth.lengthOfMonth();

        // 앞 빈칸
        for (int i = 1; i < startDay; i++) {
            calendarPanel.add(new JLabel(""));
        }
        
        String[] labels = {"제목", "내용", "시작(yyyy-MM-dd HH:mm)", "종료(yyyy-MM-dd HH:mm)"};

        for (int day = 1; day <= daysInMonth; day++) 
        {
            JPanel dayPanel = new JPanel(new BorderLayout());
            JButton dayButton = new JButton(String.valueOf(day));
            JTextArea daySchedule = new JTextArea();

            dayButton.addActionListener(e -> {
            	String baseDate = currentDate.getYear()+"-"+(currentDate.getMonthValue() < 10 ? "0" : "")+currentDate.getMonthValue()+"-" +(Integer.parseInt(dayButton.getText()) < 10 ? "0" : "")+dayButton.getText()+" ";
            	JTextField[] textFields = {
            			new JTextField(20),
            			new JTextField(20),
            			new JTextField(baseDate,20),
            			new JTextField(baseDate,20),
            			};
                JPanel labelPanel = new JPanel(new GridLayout(4, 1, 3, 3));
                JPanel inputPanel = new JPanel(new GridLayout(4, 1, 3, 3));
                JPanel topPanel = new JPanel(new BorderLayout());

                for (int i = 0; i < labels.length; i++) 
                {
                    labelPanel.add(new JLabel(labels[i]));
                    inputPanel.add(textFields[i]);
                }

                topPanel.add(labelPanel, BorderLayout.WEST);
                topPanel.add(inputPanel, BorderLayout.CENTER);

                smg = new PmsSchaduleManager();
                int result = JOptionPane.showConfirmDialog(null, topPanel, "일정 입력", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) 
                {
                    String title = textFields[0].getText();
                    String description = textFields[1].getText();
                    try 
                    {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime start = LocalDateTime.parse(textFields[2].getText(), formatter);
                        LocalDateTime end = LocalDateTime.parse(textFields[3].getText(), formatter);

                        smg.addEvent(title,description,start,end);
                        
                        ((PmsSchaduleManager)smg).setScheduleEvent(start);
                        ScheduleEvent se = ((PmsSchaduleManager)smg).getScheduleEvent();
                        daySchedule.setText(
                            se.toString()
                        );

                        // 여기에 ScheduleEvent 생성 및 저장도 가능
                        // ScheduleEvent event = new ScheduleEvent(title, description, start, end);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "날짜 형식이 잘못되었습니다.\n형식: yyyy-MM-dd HH:mm");
                        ex.printStackTrace();
                    }
                }
            });
            dayPanel.add(dayButton, BorderLayout.NORTH);
            dayPanel.add(daySchedule, BorderLayout.CENTER);
            
            calendarPanel.add(dayPanel);
            

        }
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }
    public static void main(String[] args) {
    	JFrame obj = new CalendarGUI();
    	obj.setVisible(true);
    }
}
