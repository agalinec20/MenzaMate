namespace MenzaMate.Business.Models
{
    public class AnswerDto 
    {
        public int AnswerId { get; set; }
        public string Responses { get; set; }
        public int UserId { get; set; }
        public DateTime Answered { get; set; }

        public int QuestionId { get; set; }

        public int SurveyId { get; set; }
        public string QuestionText { get; set; }
    }
}
