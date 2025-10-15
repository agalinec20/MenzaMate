using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MenzaMate.Business.Helpers
{
    public class MenuFilterHelper
    {
        public static bool AreDescriptionsSimilar(string description1, string description2, double threshold = 0.65)
        {
            var words1 = description1.Split(new[] { ',', ' ' }, StringSplitOptions.RemoveEmptyEntries).Select(w => w.ToLower()).ToHashSet();
            var words2 = description2.Split(new[] { ',', ' ' }, StringSplitOptions.RemoveEmptyEntries).Select(w => w.ToLower()).ToHashSet();

            var commonWords = words1.Intersect(words2).Count();
            var totalWords = Math.Max(words1.Count, words2.Count);

            double similarity = (double)commonWords / totalWords;
            return similarity >= threshold;
        }
    }
}
