using MenzaMate.Data.Static;
using MenzaMateBackend.Data.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace MenzaMate.Data.Entities
{
    public class Rating
    {
        public int RatingId { get; set; }
        public int MenuId { get; set; }
        public int UserId { get; set; }
        public int MenuRating {  get; set; }
        public DateTime RatingDate { get; set; }
        public string Comment { get; set; }
        public User User { get; set; }
        public Menu Menu { get; set; }

    }
    public class RatingConfigurationBuilder : IEntityTypeConfiguration<Rating>
    {
        public void Configure(EntityTypeBuilder<Rating> builder)
        {
            builder.HasKey(x => x.RatingId);

            builder
                .Property(x => x.RatingDate)
                .IsRequired();
            builder
               .Property(x => x.Comment)
               .IsRequired()
               .HasMaxLength(DataValidationConstants.LowMaxLength);

        }
    }
}

