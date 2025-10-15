using MenzaMate.Data.Enums;
using MenzaMate.Data.Static;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
namespace MenzaMate.Data.Entities
{
    public class User
    {
        public int UserId { get; set; }
        public string Role { get; set; } = RolesEnum.Student.ToString();
        public string Email { get; set; }
        public string Username { get; set; }
        public string GoogleId { get; set; }
        public ICollection<Survey> Surveys { get; set; } = new List<Survey>();
        public ICollection<Rating> Ratings { get; set; } = new List<Rating>();
        public ICollection<Answer> Answers { get; set; } = new List<Answer>();
        public ICollection<FavoriteMenu> FavoriteMenus { get; set; } = new List<FavoriteMenu>();

    }
    public class UserConfigurationBuilder : IEntityTypeConfiguration<User>
    {
        public void Configure(EntityTypeBuilder<User> builder)
        {
            builder.HasKey(x => x.UserId);
            builder
                .Property(x => x.Role)
                .IsRequired()
                .HasMaxLength(DataValidationConstants.LowMaxLength);
            builder
                .Property(x => x.Email)
                .IsRequired()
                .HasMaxLength(DataValidationConstants.LowMaxLength);
            builder
               .Property(x => x.Username)
               .IsRequired()
               .HasMaxLength(DataValidationConstants.LowMaxLength);
            builder
                .Property(x => x.GoogleId)
                .HasMaxLength(DataValidationConstants.LowMaxLength);
        }
    }
}