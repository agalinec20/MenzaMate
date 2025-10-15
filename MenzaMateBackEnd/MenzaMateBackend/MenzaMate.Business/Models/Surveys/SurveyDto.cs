namespace MenzaMate.Business.Models.Surveys
{
    public class SurveyDto : ISurveyDto
    {
        public int SurveyId { get; set; }
        public string SurveyName { get; set; }
        public DateTime SurveyDate { get; set; }

    }
}
