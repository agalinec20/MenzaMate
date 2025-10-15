namespace MenzaMate.Business.Models.Surveys
{
    public class SurveyDetailDto : ISurveyDto
    {
        public int SurveyId { get; set; }
        public string SurveyName { get; set; }
        public DateTime SurveyDate { get; set; }
        public string SurveyDescription { get; set; }
        public int UserId { get; set; }
        public List<QuestionDto> Questions { get; set; } = new List<QuestionDto>();
    }

}
