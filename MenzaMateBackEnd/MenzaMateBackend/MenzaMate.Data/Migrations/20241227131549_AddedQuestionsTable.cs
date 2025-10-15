using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

#pragma warning disable CA1814 // Prefer jagged arrays over multidimensional

namespace MenzaMate.Data.Migrations
{
    /// <inheritdoc />
    public partial class AddedQuestionsTable : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Answers_Surveys_SurveyId",
                table: "Answers");

            migrationBuilder.DropForeignKey(
                name: "FK_Menus_MenuHistories_HistoryId",
                table: "Menus");

            migrationBuilder.RenameColumn(
                name: "SurveyId",
                table: "Answers",
                newName: "QuestionId");

            migrationBuilder.RenameIndex(
                name: "IX_Answers_SurveyId",
                table: "Answers",
                newName: "IX_Answers_QuestionId");

            migrationBuilder.CreateTable(
                name: "Questions",
                columns: table => new
                {
                    QuestionId = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    SurveyId = table.Column<int>(type: "int", nullable: false),
                    QuestionText = table.Column<string>(type: "nvarchar(256)", maxLength: 256, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Questions", x => x.QuestionId);
                    table.ForeignKey(
                        name: "FK_Questions_Surveys_SurveyId",
                        column: x => x.SurveyId,
                        principalTable: "Surveys",
                        principalColumn: "SurveyId",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.UpdateData(
                table: "Answers",
                keyColumn: "Id",
                keyValue: 1,
                column: "Responses",
                value: "Blue");

            migrationBuilder.UpdateData(
                table: "Answers",
                keyColumn: "Id",
                keyValue: 2,
                columns: new[] { "QuestionId", "Responses" },
                values: new object[] { 3, "Inception" });

            migrationBuilder.InsertData(
                table: "Questions",
                columns: new[] { "QuestionId", "QuestionText", "SurveyId" },
                values: new object[,]
                {
                    { 1, "What is your favorite color?", 1 },
                    { 2, "What is your favorite food?", 1 },
                    { 3, "What is your favorite movie?", 2 },
                    { 4, "What is your favorite book?", 2 }
                });

            migrationBuilder.CreateIndex(
                name: "IX_Questions_SurveyId",
                table: "Questions",
                column: "SurveyId");

            migrationBuilder.AddForeignKey(
                name: "FK_Answers_Questions_QuestionId",
                table: "Answers",
                column: "QuestionId",
                principalTable: "Questions",
                principalColumn: "QuestionId",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Menus_MenuHistories_HistoryId",
                table: "Menus",
                column: "HistoryId",
                principalTable: "MenuHistories",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Answers_Questions_QuestionId",
                table: "Answers");

            migrationBuilder.DropForeignKey(
                name: "FK_Menus_MenuHistories_HistoryId",
                table: "Menus");

            migrationBuilder.DropTable(
                name: "Questions");

            migrationBuilder.RenameColumn(
                name: "Responses",
                table: "Answers",
                newName: "Reponses");

            migrationBuilder.RenameColumn(
                name: "QuestionId",
                table: "Answers",
                newName: "SurveyId");

            migrationBuilder.RenameIndex(
                name: "IX_Answers_QuestionId",
                table: "Answers",
                newName: "IX_Answers_SurveyId");

            migrationBuilder.UpdateData(
                table: "Answers",
                keyColumn: "Id",
                keyValue: 1,
                column: "Reponses",
                value: "Yes");

            migrationBuilder.UpdateData(
                table: "Answers",
                keyColumn: "Id",
                keyValue: 2,
                columns: new[] { "Reponses", "SurveyId" },
                values: new object[] { "No", 2 });

            migrationBuilder.AddForeignKey(
                name: "FK_Answers_Surveys_SurveyId",
                table: "Answers",
                column: "SurveyId",
                principalTable: "Surveys",
                principalColumn: "SurveyId",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_Menus_MenuHistories_HistoryId",
                table: "Menus",
                column: "HistoryId",
                principalTable: "MenuHistories",
                principalColumn: "Id");
        }
    }
}
