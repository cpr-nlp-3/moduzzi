# api_summarize.py
#
# 강의평 요약
#
# input: (string) review raw text
# output: (string) summary text

import os
import sys
import requests
import json
import re
import io

#import konlpy
#from konlpy.tag import Okt

# api 키 저장
#import key
sys.stdout = io.TextIOWrapper(sys.stdout.detach(), encoding="utf-8")
sys.stderr = io.TextIOWrapper(sys.stderr.detach(), encoding="utf-8")

def summarize(content="", tone=3, summaryCount=3):
    #client_id = key.client_id
    #client_secret = key.client_secret
    client_id = os.environ['CLOVA_CLIENT_ID']
    client_secret = os.environ['CLOVA_CLIENT_SECRET']
    url = 'https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize'

    if len(content) <= 0:
        return ""

    tone = tone
    summaryCount = summaryCount

    headers = {
        'Accept': 'application/json;UTF-8',
        'Content-Type': 'application/json;UTF-8',
        'X-NCP-APIGW-API-KEY-ID': client_id,
        'X-NCP-APIGW-API-KEY': client_secret,
    }

    data = {
        "document": {
            "content": content
        },
        "option": {
            "language": "ko",
            "model": "general",
            "tone": tone,
            "summaryCount": summaryCount,
        },
    }

    response = requests.post(
        url,
        headers = headers,
        data = json.dumps(data).encode('UTF-8'))

    rescode = response.status_code
    if(rescode != 200):
        # 에러 발생 시 현재 처리중인 텍스트 건너뛰기
        return "*** [Error] summarize : " + response.text

    summary_raw = response.text

    #okt = Okt()

    # json 형태의 response에서 요약 text만 추출
    summary = summary_raw.split(":")[1].lstrip("\"").rstrip("\"}")
    # 줄바꿈, 공백 처리
    #summary = re.sub(r'\\n', ' ', summary) #요약된 문장들을 \n로 구분하기 위해 주석처리
    summary = re.sub('\s+', ' ', summary)
    # 문장 분리
    # print(type(okt.pos(summary)), okt.pos(summary))

    return summary

data = sys.argv[1]
data = re.sub("[\n\s]+", " ", data)

# data의 각 요소가 차례로 함수에 인자로 전달됨
# tone, 요약 문장수를 바꾸고 싶은 경우
# java 코드에서 ProcessBuilder로 python 함수를 불러올 때
# "tone=n" 이렇게 직접 파라미터값을 설정해 주면 됨
result = summarize(data)

print(result)