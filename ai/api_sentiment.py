# api_sentiment.py
#
# 강의평 요약
#
# input: (string) review raw text
# output: (json) sentiment

import os
import sys
import requests
import json

# api 키 저장
import key
      
def sentiment(content=""): 
    client_id = os.environ['CLOVA_CLIENT_ID']
    client_secret = os.environ['CLOVA_CLIENT_SECRET']
    url = 'https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze'

    if len(content) <= 0:
        return 0

    headers = {
            'Accept': 'application/json;UTF-8',
            'Content-Type': 'application/json;UTF-8',
            'X-NCP-APIGW-API-KEY-ID': client_id,
            'X-NCP-APIGW-API-KEY': client_secret,
        }
    
    data = {
        "content": content
    }
    
    json.dumps(data, indent=4, sort_keys=True)
    response = requests.post(
        url, 
        headers = headers, 
        data = json.dumps(data).encode('UTF-8'))

    rescode = response.status_code
    if(rescode != 200):
        # 에러 발생 시 현재 처리중인 텍스트 건너뛰기
        return "*** [Error] sentiment : " + response.text
        
    sentiment = response.text
    return sentiment

data = sys.argv[1:]

# data의 각 요소가 차례로 함수에 인자로 전달됨
# 인자가 1개 초과하면 에러
result = sentiment(data)

print(result)