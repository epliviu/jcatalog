package ro.tm.siit.w10h;

public enum FeedbackType {

	HighSatistfaction((byte) 9, "I'm very happy. 10x!!!"), 
	AverageSatisfaction((byte) 7, "I suppose it is ok."), 
	BelowSatisfaction((byte) 0, "Not happy with my rating!");

	private byte minGrade;
	private String feedbackMsg;

	private FeedbackType(byte minGrade, String feedbackMsg) {
		this.minGrade = minGrade;
		this.feedbackMsg = feedbackMsg;
	}

	public String getMessage() {
		return feedbackMsg;
	}

	public static FeedbackType valueOfGrade(int grade) {
		for (FeedbackType type : values()) {
			if (grade >= type.minGrade) {
				return type;
			}
		}
		throw new IllegalArgumentException("no FeedbackType for " + grade);
	}

}

