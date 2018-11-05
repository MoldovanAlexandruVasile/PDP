using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
 
namespace WebDownloader
{
    class Program
    {
        private static List<string> pages = new List<string>();
        static void Main(string[] args)
        {
            pages.Add(@"https://stackoverflow.com/questions/42077388/type-used-in-a-using-statement-must-be-implicitly-convertible-to-system-idispos");
            pages.Add(@"https://stackoverflow.com/questions/12931687/targetinvocationexception-occurred-but-i-have-absolutely-no-idea-why");
            pages.Add(@"https://stackoverflow.com/questions/3458046/how-to-include-quotes-in-a-string");
            Task t = new Task(DownloadPageAsync);
            t.Start();
            Console.ReadLine();
        }
 
        static async void DownloadPageAsync()
        {
            HttpClient client = new HttpClient();
            for (int i = 0; i < pages.Count; i++) {
                string page = pages.ElementAt(i);
                HttpResponseMessage response = await client.GetAsync(page);
                HttpContent content = response.Content;
                {
                    string result = await content.ReadAsStringAsync();
                    Console.WriteLine(result);
                    separator();
                }
            }
        }
 
        static void DownloadPageEvent()
        {
            for (int i = 0; i < pages.Count; i++)
            {
                using (WebClient client = new WebClient())
                {
                    client.DownloadStringCompleted += (sender, e) =>
                    {
                        Console.WriteLine(e.Result);
                    };

                    string page = pages.ElementAt(i);
                    client.DownloadStringAsync(new Uri(page));
                    separator();
                }
            }
        }

        static void separator()
        {
            Console.WriteLine("========================================================================================================================");
        }
    }
}