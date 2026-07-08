from fastapi import status


def test_generate_result_success(
    client,
    student_token,
):
    """
    Verify that a quiz result is generated successfully after submitting a quiz attempt
    """

    response = client.get(
        "/results/latest",
        headers={
            "Authorization": f"Bearer {student_token}",
        },
    )

    assert response.status_code == status.HTTP_200_OK

    data = response.json()

    assert "id" in data
    assert "attempt_id" in data
    assert "quiz_id" in data
    assert "score" in data
    assert "percentage" in data
    assert "result_status" in data