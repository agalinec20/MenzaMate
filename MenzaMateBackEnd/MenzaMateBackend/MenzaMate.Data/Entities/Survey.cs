using MenzaMate.Data.Static;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace MenzaMate.Data.Entities
{
    public class Survey
    {
        public int SurveyId { get; set; }
        public string SurveyName { get; set; }
        public string SurveyDescription { get; set; }
        public DateTime SurveyDate { get; set; }
        public int UserId { get; set; }
        public User User { get; set; }
        public ICollection<Question> Questions { get; set; } = new List<Question>();


    }
    public class SurveyConfigurationBuilder : IEntityTypeConfiguration<Survey>
    {
        public void Configure(EntityTypeBuilder<Survey> builder)
        {
            builder.HasKey(x => x.SurveyId);

            builder
                .Property(x => x.SurveyName)
                .IsRequired()
                .HasMaxLength(DataValidationConstants.LowMaxLength);

            builder
                .Property(x => x.SurveyDescription)
                .IsRequired();
        }
    }
}
