using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace MenzaMate.Data.Migrations
{
    /// <inheritdoc />
    public partial class AddedMenuRatingInRatings : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "MenuRating",
                table: "Ratings",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.UpdateData(
                table: "Ratings",
                keyColumn: "RatingId",
                keyValue: 1,
                column: "MenuRating",
                value: 0);

            migrationBuilder.UpdateData(
                table: "Ratings",
                keyColumn: "RatingId",
                keyValue: 2,
                column: "MenuRating",
                value: 0);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "MenuRating",
                table: "Ratings");
        }
    }
}
