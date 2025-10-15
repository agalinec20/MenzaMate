using MenzaMateBackend.Data.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace MenzaMate.Data.Entities
{
    public class FavoriteMenu
    {
        public int FavoriteMenuId { get; set; }
        public int UserId { get; set; }
        public int MenuId { get; set; }
        public DateTime Added { get; set; }
        public User User { get; set; }
        public Menu Menu { get; set; }
    }
    public class FavoriteMenuConfigurationBuilder : IEntityTypeConfiguration<FavoriteMenu>
    {
        public void Configure(EntityTypeBuilder<FavoriteMenu> builder)
        {
            builder.HasKey(x => x.FavoriteMenuId);
            builder.ToTable("FavoriteMenus");

        }
    }
}
