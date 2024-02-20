# import sys
import os
import requests
import json
import re

import key
client_id = key.client_id
client_secret = key.client_secret

url = 'https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize'
url2 = 'https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze'

headers = {
            'Accept': 'application/json;UTF-8',
            'Content-Type': 'application/json;UTF-8',
            'X-NCP-APIGW-API-KEY-ID': client_id,
            'X-NCP-APIGW-API-KEY': client_secret,
        }

root_path = os.getcwd()
textfile = 'test.txt'
textfile_path = root_path + '/ai/' + textfile
with open(textfile_path, 'r', encoding='utf-8') as file:
    content = file.read()
# content = re.sub('\n', ' ', content)

tone = 3
summaryCount = 5

# data = {
#   "document": {
#     "content": content
#   },
#   "option": {
#     "language": "ko",
#     "model": "general",
#     "tone": tone,
#     "summaryCount": summaryCount,
#   },
# }

# response = requests.post(
#     url, 
#     headers=headers, 
#     data=json.dumps(data).encode('UTF-8'))

# rescode = response.status_code
# if(rescode != 200):
#     print("Error : " + response.text)
#     exit(1) # 에러 발생 시 프로그램 종료
    
# summary_raw = response.text.split(":")[1].lstrip("\"").rstrip("\"}")

# summary = summary_raw.split("\\n")
# for i, line in enumerate(summary):
#     summary[i] = re.sub('[.,!?]', '', line)
# print(summary)

data = {
    "content": content
}
json.dumps(data, indent=4, sort_keys=True)
response = requests.post(
    url2, 
    headers=headers, 
    data=json.dumps(data).encode('UTF-8'))

rescode = response.status_code
if(rescode != 200):
    print("Error : " + response.text)
    exit(1) # 에러 발생 시 프로그램 종료
# print(response.text)
with open("./ai/out.txt", "w") as file:
    file.write(response.text)