using MenzaMate.Data.Static;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace MenzaMate.Data.Entities
{
    public class Answer
    {
        public int AnswerId { get; set; }
        public int UserId { get; set; }
        public int QuestionId { get; set; } 
        public string Responses { get; set; }
        public DateTime Answered { get; set; }
        public User User { get; set; }
        public Question Question { get; set; }
    }

    public class AnswerConfigurationBuilder : IEntityTypeConfiguration<Answer>
    {
        public void Configure(EntityTypeBuilder<Answer> builder)
        {
            builder.HasKey(a => a.AnswerId);
          
            builder.Property(a => a.Responses)
                   .IsRequired()
                   .HasMaxLength(DataValidationConstants.LowMaxLength);

            builder.Property(a => a.Answered)
                   .IsRequired();

        }
    }

}
