using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

#pragma warning disable CA1814 // Prefer jagged arrays over multidimensional

namespace MenzaMate.Data.Migrations
{
    /// <inheritdoc />
    public partial class UpdatedArchitecture : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "MenuHistories",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Viewed = table.Column<DateTime>(type: "datetime2", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_MenuHistories", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Users",
                columns: table => new
                {
                    UserId = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Role = table.Column<string>(type: "nvarchar(256)", maxLength: 256, nullable: false),
                    Email = table.Column<string>(type: "nvarchar(256)", maxLength: 256, nullable: false),
                    Username = table.Column<string>(type: "nvarchar(256)", maxLength: 256, nullable: false),
                    GoogleId = table.Column<string>(type: "nvarchar(256)", maxLength: 256, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Users", x => x.UserId);
                });

            migrationBuilder.CreateTable(
                name: "Menus",
                columns: table => new
                {
                    MenuId = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    HistoryId = table.Column<int>(type: "int", nullable: false),
                    Date = table.Column<DateTime>(type: "datetime2", nullable: false),
                    Title = table.Column<string>(type: "nvarchar(256)", maxLength: 256, nullable: false),
                    Description = table.Column<string>(type: "nvarchar(256)", maxLength: 256, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Menus", x => x.MenuId);
                    table.ForeignKey(
                        name: "FK_Menus_MenuHistories_HistoryId",
                        column: x => x.HistoryId,
                        principalTable: "MenuHistories",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Surveys",
                columns: table => new
                {
                    SurveyId = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    SurveyName = table.Column<string>(type: "nvarchar(256)", maxLength: 256, nullable: false),
                    SurveyDescription = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    SurveyDate = table.Column<DateTime>(type: "datetime2", nullable: false),
                    UserId = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Surveys", x => x.SurveyId);
                    table.ForeignKey(
                        name: "FK_Surveys_Users_UserId",
                        column: x => x.UserId,
                        principalTable: "Users",
                        principalColumn: "UserId",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "FavoriteMenus",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    UserId = table.Column<int>(type: "int", nullable: false),
                    MenuId = table.Column<int>(type: "int", nullable: false),
                    Added = table.Column<DateTime>(type: "datetime2", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_FavoriteMenus", x => x.Id);
                    table.ForeignKey(
                        name: "FK_FavoriteMenus_Menus_MenuId",
                        column: x => x.MenuId,
                        principalTable: "Menus",
                        principalColumn: "MenuId",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_FavoriteMenus_Users_UserId",
                        column: x => x.UserId,
                        principalTable: "Users",
                        principalColumn: "UserId",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Ratings",
                columns: table => new
                {
                    RatingId = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    MenuId = table.Column<int>(type: "int", nullable: false),
                    UserId = table.Column<int>(type: "int", nullable: false),
                    RatingDate = table.Column<DateTime>(type: "datetime2", nullable: false),
                    Comment = table.Column<string>(type: "nvarchar(256)", maxLength: 256, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Ratings", x => x.RatingId);
                    table.ForeignKey(
                        name: "FK_Ratings_Menus_MenuId",
                        column: x => x.MenuId,
                        principalTable: "Menus",
                        principalColumn: "MenuId",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Ratings_Users_UserId",
                        column: x => x.UserId,
                        principalTable: "Users",
                        principalColumn: "UserId",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Answers",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    UserId = table.Column<int>(type: "int", nullable: false),
                    SurveyId = table.Column<int>(type: "int", nullable: false),
                    Reponses = table.Column<string>(type: "nvarchar(256)", maxLength: 256, nullable: false),
                    Answered = table.Column<DateTime>(type: "datetime2", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Answers", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Answers_Surveys_SurveyId",
                        column: x => x.SurveyId,
                        principalTable: "Surveys",
                        principalColumn: "SurveyId",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Answers_Users_UserId",
                        column: x => x.UserId,
                        principalTable: "Users",
                        principalColumn: "UserId",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.InsertData(
                table: "MenuHistories",
                columns: new[] { "Id", "Viewed" },
                values: new object[,]
                {
                    { 1, new DateTime(2024, 1, 15, 12, 0, 0, 0, DateTimeKind.Unspecified) },
                    { 2, new DateTime(2024, 2, 15, 12, 0, 0, 0, DateTimeKind.Unspecified) }
                });

            migrationBuilder.InsertData(
                table: "Users",
                columns: new[] { "UserId", "Email", "GoogleId", "Role", "Username" },
                values: new object[,]
                {
                    { 1, "admin@example.com", "google-id-12345", "Admin", "admin_user" },
                    { 2, "student@example.com", "google12", "Student", "student_user" }
                });

            migrationBuilder.InsertData(
                table: "Menus",
                columns: new[] { "MenuId", "Date", "Description", "HistoryId", "Title" },
                values: new object[,]
                {
                    { 1, new DateTime(2024, 1, 16, 0, 0, 0, 0, DateTimeKind.Unspecified), "Eggs, toast, and coffee.", 1, "Breakfast Menu" },
                    { 2, new DateTime(2024, 2, 16, 0, 0, 0, 0, DateTimeKind.Unspecified), "Chicken, rice, and salad.", 2, "Lunch Menu" }
                });

            migrationBuilder.InsertData(
                table: "Surveys",
                columns: new[] { "SurveyId", "SurveyDate", "SurveyDescription", "SurveyName", "UserId" },
                values: new object[,]
                {
                    { 1, new DateTime(2024, 1, 1, 12, 0, 0, 0, DateTimeKind.Unspecified), "This is a description of the first survey.", "First Survey", 1 },
                    { 2, new DateTime(2024, 2, 1, 12, 0, 0, 0, DateTimeKind.Unspecified), "This is a description of the second survey.", "Second Survey", 2 }
                });

            migrationBuilder.InsertData(
                table: "Answers",
                columns: new[] { "Id", "Answered", "Reponses", "SurveyId", "UserId" },
                values: new object[,]
                {
                    { 1, new DateTime(2024, 1, 1, 13, 0, 0, 0, DateTimeKind.Unspecified), "Yes", 1, 1 },
                    { 2, new DateTime(2024, 2, 1, 13, 0, 0, 0, DateTimeKind.Unspecified), "No", 2, 2 }
                });

            migrationBuilder.InsertData(
                table: "FavoriteMenus",
                columns: new[] { "Id", "Added", "MenuId", "UserId" },
                values: new object[,]
                {
                    { 1, new DateTime(2024, 1, 17, 0, 0, 0, 0, DateTimeKind.Unspecified), 1, 1 },
                    { 2, new DateTime(2024, 2, 17, 0, 0, 0, 0, DateTimeKind.Unspecified), 2, 2 }
                });

            migrationBuilder.InsertData(
                table: "Ratings",
                columns: new[] { "RatingId", "Comment", "MenuId", "RatingDate", "UserId" },
                values: new object[,]
                {
                    { 1, "Delicious breakfast!", 1, new DateTime(2024, 1, 16, 14, 0, 0, 0, DateTimeKind.Unspecified), 1 },
                    { 2, "Very satisfying lunch.", 2, new DateTime(2024, 2, 16, 14, 0, 0, 0, DateTimeKind.Unspecified), 2 }
                });

            migrationBuilder.CreateIndex(
                name: "IX_Answers_SurveyId",
                table: "Answers",
                column: "SurveyId");

            migrationBuilder.CreateIndex(
                name: "IX_Answers_UserId",
                table: "Answers",
                column: "UserId");

            migrationBuilder.CreateIndex(
                name: "IX_FavoriteMenus_MenuId",
                table: "FavoriteMenus",
                column: "MenuId");

            migrationBuilder.CreateIndex(
                name: "IX_FavoriteMenus_UserId",
                table: "FavoriteMenus",
                column: "UserId");

            migrationBuilder.CreateIndex(
                name: "IX_Menus_HistoryId",
                table: "Menus",
                column: "HistoryId");

            migrationBuilder.CreateIndex(
                name: "IX_Ratings_MenuId",
                table: "Ratings",
                column: "MenuId");

            migrationBuilder.CreateIndex(
                name: "IX_Ratings_UserId",
                table: "Ratings",
                column: "UserId");

            migrationBuilder.CreateIndex(
                name: "IX_Surveys_UserId",
                table: "Surveys",
                column: "UserId");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Answers");

            migrationBuilder.DropTable(
                name: "FavoriteMenus");

            migrationBuilder.DropTable(
                name: "Ratings");

            migrationBuilder.DropTable(
                name: "Surveys");

            migrationBuilder.DropTable(
                name: "Menus");

            migrationBuilder.DropTable(
                name: "Users");

            migrationBuilder.DropTable(
                name: "MenuHistories");
        }
    }
}
