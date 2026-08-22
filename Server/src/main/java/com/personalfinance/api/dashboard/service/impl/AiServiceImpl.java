package com.personalfinance.api.dashboard.service.impl;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.personalfinance.api.dashboard.dto.response.AnalysisResponse;
import com.personalfinance.api.dashboard.service.AiService;
import com.personalfinance.exception.AppException;
import com.personalfinance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final ObjectMapper objectMapper;
    private final Client geminiClient;

    @Override
    public String analyze(AnalysisResponse response) {
        try {
            String jsonData = objectMapper.writeValueAsString(response);
            String prompt = """
                    Bạn là AI chuyên phân tích tài chính cá nhân.
                    Hãy phân tích dữ liệu tài chính của người dùng dựa trên
                    dữ liệu JSON được cung cấp bên dưới.
                    Hãy phân tích:
                    1. So sánh thu nhập, chi tiêu và tiết kiệm với tháng trước.
                    2. Phân tích các budget đã vượt mức và đưa ra đề xuất
                       để hạn chế vượt budget.
                    3. Phân tích các payment reminder.
                       Nếu người dùng thanh toán các khoản này thì số dư
                       dự kiến sẽ thay đổi như thế nào.
                    4. Phân tích các saving goal đang IN_PROGRESS.
                       Dựa trên tốc độ tiết kiệm hiện tại, nhận xét tiến độ
                       và khả năng đạt mục tiêu.
                    5. Phân tích các category.
                       Xác định category chi tiêu nhiều và những category
                       nên được tối ưu.
                    6. Phân tích các transaction trong tháng:
                       - khoản chi lớn
                       - khoản chi lặp lại
                       - mức chi tiêu
                       - khoản chi bất thường
                       - khoản chi có thể cân nhắc cắt giảm
                    7. Đề xuất cách tối ưu:
                       - thu nhập
                       - chi tiêu
                       - tiết kiệm
                       - ngân sách
                    8. Đưa ra lời khuyên thực tế dựa trên dữ liệu.
                    Không được tự bịa số liệu.
                    Chỉ sử dụng những số liệu có trong dữ liệu được cung cấp.
                    Hãy trả lời bằng tiếng Việt, rõ ràng và dễ hiểu.
                    Dữ liệu tài chính:
                    %s
                    """.formatted(jsonData);
            GenerateContentResponse aiResponse = geminiClient.models
                    .generateContent("gemini-3.1-flash-lite", prompt, null);
            String text = aiResponse.text();

            if (text == null || text.isBlank()) {
                throw new AppException(ErrorCode.AI_ANALYSIS_FAILED);
            }

            return text;

        } catch (JacksonException e) {
            throw new AppException(ErrorCode.AI_ANALYSIS_FAILED);
        }
    }
}
