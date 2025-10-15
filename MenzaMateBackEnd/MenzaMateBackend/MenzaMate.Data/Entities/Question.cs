using MenzaMate.Data.Static;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace MenzaMate.Data.Entities
{
    public class Question
    {
        public int QuestionId { get; set; }
        public int SurveyId { get; set; }
        public string QuestionText { get; set; }
        public Survey Survey { get; set; }
        public ICollection<Answer> Answers { get; set; } = new List<Answer>();
    }
    public class QuestionConfigurationBuilder : IEntityTypeConfiguration<Question>
    {
        public void Configure(EntityTypeBuilder<Question> builder)
        {
            builder.HasKey(q => q.QuestionId);

            builder.Property(q => q.QuestionText)
                   .IsRequired()
                   .HasMaxLength(DataValidationConstants.LowMaxLength);

        }
    }
}
